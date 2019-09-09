println "${regularExpressionPattern}"
println "${gitRepositoryUrl}"

if ((matches = (gitRepositoryUrl =~ regularExpressionPattern))) {
	String projectFolder = matches.group('projectOrganization')
	String projectJobPath = "${projectFolder}/${matches.group('projectName')}"
	folder(projectFolder) {
		multibranchPipelineJob(projectJobPath) {
			configure {
				it / sources / 'data' / 'jenkins.branch.BranchSource' << {
					source(class: 'jenkins.plugins.git.GitSCMSource') {
						credentialsId('GITHUB_SSH_KEY')
						remote(gitRepositoryUrl)
						traits {
							'jenkins.plugins.git.traits.TagDiscoveryTrait'()
							'jenkins.plugins.git.traits.BranchDiscoveryTrait'()
							'jenkins.scm.impl.trait.WildcardSCMHeadFilterTrait' {
								includes('*')
								excludes('*-skip-ci')
							}
						}
					}
					strategy(class: 'jenkins.branch.DefaultBranchPropertyStrategy') {
						properties(class: 'java.util.Arrays\$ArrayList') {
							a(class: 'jenkins.branch.BranchProperty-array') {
								'jenkins.branch.NoTriggerBranchProperty'()
							}
						}
					}
				}
			}
		}
	}
} else {
	println "The '${gitRepositoryUrl}' repository did not match '${regularExpressionPattern}'."
}
