//Trigger eMail Notification
def triggerEmail() {
	emailext( 
		to: gitHubProps.TO_RECIPIENT,
		subject: '${DEFAULT_SUBJECT}', 
		body: '${DEFAULT_CONTENT}'
	)
	echo "Status Mail Send Success"
} 


//Upload to Artifactory
def uploadToArtifactory() {
	script {
		server = Artifactory.server artProps.ARTIFACTORY_SERVER
		def uploadSpec = """{
			"files": [
        		{
        			"pattern": "target/*.war",
            		"target": "demo-java/target/Build #${env.BUILD_NUMBER}/"
        		}
        		]
    		}"""
    		echo "Artifact Archival Success"
    	server.upload(uploadSpec) 
		echo "Artifact Upload Success"
	}

}


def flushWorkspace() {
	script {
		sh 'rm -rf ../'+env.JOB_NAME+'/*'
	}
	echo "Workspace Cleaned"
}

return this