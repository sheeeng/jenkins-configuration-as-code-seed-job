import static groovy.json.JsonOutput.prettyPrint
import static groovy.json.JsonOutput.toJson


pipeline {
	agent {
		label 'jenkins-label'
	}
	options {
		timestamps()
	}
	stages {
		stage('Seed Jobs') {
			steps {
				script {
					def extractedRepositories = readYaml file: 'repositories.yaml'
					echo "Extracted data from repositories.yaml file:\n${prettyPrint(toJson(extractedRepositories))}"
					for (String url: extractedRepositories.gitRepositoryUrls) {
						jobDsl(
							targets: 'build.groovy',
							failOnMissingPlugin: true,
							removedJobAction: 'DISABLE',
							removedViewAction: 'DELETE',
							unstableOnDeprecation: true,
							additionalParameters: [
								regularExpressionPattern: extractedRepositories.regularExpressionPattern,
								gitRepositoryUrl: url
							]
						)
					}
					echo "\nSeed Job execution completed sucessfully."
				}
			}
		}
	}
}
