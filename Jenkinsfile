pipeline {
	agent {
		docker {
			image "maven:3.8.1-jdk-8"
		}
	}
	
	stages {
		stage("Build") {
			steps {
				sh "mvn -version"
				sh "mvn clean install"
			}
		}
	}
	
	post {
		always {
			cleanWs()
		}
	}
}