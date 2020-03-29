// global variables
git_server="http://github.com/mouachan"
github_project = 'jwtsidecar'
git_branch = "master"
artifact = ""

// namespaces
def namespace_dev = "pocfusedev-lab"
def namespace_acp = "pocfuseacp-lab"
def namespace_prd = "pocfuseprd-lab"		


/* version management
 * image version should be parameterized
 * git tag name?
*/
def appname = "int-poc-jwtsidecar"
def image_version = "${params.IMAGE_VERSION}"
def publish_sidecar = "${params.NAMESPACE_PROD}"

def registry = "docker-registry.default.svc:5000"

// maven variables
def version



def generateConfigMap(namespace) {
    sh "oc delete cm ${artifact} -n ${namespace} --ignore-not-found=true"
	sh "oc create cm ${artifact} --from-file=src/main/resources/application.properties -n ${namespace}"
	return 0
}
def setEnvVars(namespace){
    def sso_server
    def sso_realm
    def sso_key
    
    if ( "${namespace}" == "pocfusedev-lab") {
        sso_server='<server>'
        sso_realm='<realm>'
        sso_key='<key>'
    } else {
        // at the moment we keep the same info as dev
        sso_server='<server>'
        sso_realm='<realm>'
        sso_key=''
    }
    
    sh "oc set env -n ${namespace} dc/openidconnect-gateway SSO_SERVER_URL=${sso_server}"
    sh "oc set env -n ${namespace} dc/openidconnect-gateway SSO_REALM=${sso_realm}"
    sh "oc set env -n ${namespace} dc/openidconnect-gateway SSO_SERVER_RSA_PUBLIC_KEY=${sso_key}"
}

    pipeline {
        options {
            timeout(time: 1, unit: 'HOURS')
            skipDefaultCheckout()
        }
    	environment {
    	    VAR = 'VAL'
    		//KUBERNETES_MASTER = "https://oselab.mutua.es:8443"
    	}
        
        tools {
    		maven 'maven-3' // Maven installation declared in the Jenkins "Global Tool Configuration"
    		//jdk 'jdk8'
        }
        
        agent none
        
        stages {
    		
    		
    	   // prior to this stage, the code might have to be moved to another branch ???
    	   
    	   stage('GitHub-checkout') {
    	       agent any
    		   steps {
    		       step([$class: 'WsCleanup'])
    			   git(
    			    url: "https://${git_server}/${github_project}.git",
    				credentialsId: 'mouachan', // from credentials.xml in Jenkins
    				branch: "${git_branch}"
    				)
    				
    				//def version = sh 'git describe --tags --abbrev=0' 	// if version in git tag
    			}
    	   }
    
            
           stage('Build-artefact') {
                agent any
                steps {
                    script {
                        pom = readMavenPom file: 'pom.xml'
                        artifact=pom.artifactId
                        version=pom.version
                    }
                    script {
    					
                        withMaven(
    						maven: 'maven-3',
    						mavenSettingsConfig: 'poc-maven-settings'	// Maven settings.xml file defined with the Jenkins Config File Provider Plugin
    						) 
    					{
    						sh "mvn clean package"
    					}
    				}
    			}
           }
    		
    			
            stage('DeployTo-dev') {
                agent any
                steps {
                    sh "oc login https://<openshift_server>:8443 --username=<user> --password=<passwd>"
    				script {
    					openshift.withCluster('ocp-poc', 'ocp') {	
    					        openshift.withProject("${namespace_dev}") {
    						        echo "Using project: ${openshift.project()}"
    						
    						
    						        echo "building container image"
    						        sh "mvn fabric8:build -Dmaven.test.skip -Dimage.version=latest -Dfabric8.namespace=${namespace_dev}"
    						
						            echo "generating ConfigMap"
						            generateConfigMap(namespace_dev)    	
    					
						            sh "oc process mutua-fuse-openidconnect-template -n openshift --param APP_NAME=${artifact} --param APP_VERSION=${version} --param IMAGE_VERSION=latest | oc apply -n ${namespace_dev} -f -"
						            /*def rm = openshift.selector("dc", "${artifact}").rollout()
                  				    timeout(5) { 
                    					openshift.selector("dc", "${artifact}").related('pods').untilEach(1) {
                      						return (it.object().status.phase == "Running")
							            }
                    				}*/
                    				
                    				echo "configuring Red Hat SSO parameters"
                    				setEnvVars(namespace_dev)
                    				
    					    }
    					}
    				}
    			}
            }
            
            
            
            
			stage('PromoteTo-acp') {
				agent any
				steps{
				    input message: 'Generate ACP resources?'
						script {
						
							withMaven(
								maven: 'maven-3',
								mavenSettingsConfig: 'poc-maven-settings'	
								) 
							{
							    echo "creating openshift deployment objects"
				
												
								echo "generating ConfigMap"
								generateConfigMap(namespace_acp)

							    sh "oc process mutua-fuse-openidconnect-template -n openshift --param APP_NAME=${artifact} --param APP_VERSION=${version} --param IMAGE_VERSION=${image_version} | oc apply -n ${namespace_acp} -f -"
														
							    /*def rm = openshift.selector("dc", "${artifact}").rollout()
                  				timeout(5) { 
                    					openshift.selector("dc", "${artifact}").related('pods').untilEach(1) {
                      						return (it.object().status.phase == "Running")
						    	        }
                    			}*/
								

							    setEnvVars(namespace_acp)

    								//openshift.withCluster('ocp-poc','ocp-login') {					
    				    				//openshift.withProject("${namespace_acp}") {
									echo "promoting image"
									sh "oc tag ${namespace_dev}/${artifact}:latest ${namespace_acp}/${artifact}:${image_version}"
								//}}
								
							}
						}
					}
			}      
        
            stage('Integration-tests') {
                agent any
                steps{
                    script{echo "performing Integration tests (dry-run)"}
                }
            }
            stage('Performance-tests') {
                agent any
                steps{
                    script{echo "performing Performance tests (dry-run)"}
                }
            }        
        
        stage('GoLive Approval') {
                agent none
                steps {
                        input message: 'Are you sure you want to deploy to Production?'
                }
        }
                  
        stage('GoLive') {
				agent any
				steps{
						script {
						
							withMaven(
								maven: 'maven-3',
								mavenSettingsConfig: 'poc-maven-settings'	
								) 
							{
							    echo "creating openshift deployment objects"
				
												
									echo "promoting image"
									sh "oc tag ${namespace_acp}/${artifact}:${image_version} ${publish_sidecar}/${artifact}:${image_version}"
								//}}
								
							}
						}
					}
		}
		
    	
		
    }	
    
}
