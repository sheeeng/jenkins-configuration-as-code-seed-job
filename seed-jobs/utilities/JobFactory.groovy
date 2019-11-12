#!/usr/bin/env groovy

package utilities

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import javaposse.jobdsl.dsl.Folder
import javaposse.jobdsl.dsl.jobs.WorkflowJob
import javaposse.jobdsl.dsl.jobs.MultibranchWorkflowJob
import javaposse.jobdsl.dsl.helpers.scm.GitContext

/* Apart from the limited built-in API, Job DSL supports many more Jenkins plugins at runtime.
 * https://jenkinsci.github.io/job-dsl-plugin/
 *
 * The complete API reference is available in your Jenkins installation at
 * https://your.jenkins.installation/plugin/job-dsl/api-viewer/index.html.
 *
 * /

/**
 * Base DSL templates for all Jenkins jobs.
 *
 */
public class JobFactory {
    private DslFactory dslFactory
    private String credentialsId = 'GITHUB_SSH_KEY'

    JobFactory(DslFactory dslFactory) {
        this.dslFactory = dslFactory
    }

    Job createJob(Map args) {
        dslFactory.job(args.name) {
            description(args.jobDescription)
            scm {
                git {
                    remote {
                        url(args.gitUrl)
                        credentials(credentialsId)
                    }
                    branch(args.gitBranch)
                }
            }
            publishers {
                downstream(args.downstreamJob, 'SUCCESS')
            }
            triggers {
                scm(args.triggerScm)
            }
        }
    }

    /**
     * Basic job for freestyle independent jobs.
     *
     * @param name Job name in Jenkins.
     * @param jobDescription Job description in Jenkins UI.
     * @param jenkinsfilePath Path to Jenkinsfile inside repo
     * @param gitUrl URL to git repo
     * @param gitBranch Branch to check out
     *
     * @return Jenkins Job structure object.
     *
     */

    WorkflowJob createPipelineJob(Map args) {
        dslFactory.pipelineJob(args.name) {
            description(args.jobDescription)
            definition {
                cpsScm {
                    scriptPath(args.jenkinsfilePath)
                    scm {
                        git {
                            remote {
                                url(args.gitUrl)
                                credentials(credentialsId)
                            }
                            branches(args.gitBranch)
                        }
                    }
                }
            }
        }
    }

    WorkflowJob pipelineJobWithSubmodules(Map args) {
        dslFactory.pipelineJob(args.name) {
            description(args.jobDescription)
            definition {
                cpsScm {
                    scriptPath(args.jenkinsfilePath)
                    scm {
                        git {
                            extensions {
                                submoduleOptions {
                                    disable(false)
                                    recursive(true)
                                }
                            }
                            remote {
                                url(args.gitUrl)
                                credentials(credentialsId)
                            }
                            branches(args.gitBranch)
                        }
                    }
                }
            }
        }
    }

    Folder createFolder(Map args) {
        dslFactory.folder(args.name) {
            description(args.description)
        }
    }

    MultibranchWorkflowJob createMultiBranchPipeline(Map args) {
        dslFactory.multibranchPipelineJob(args.name) {
            description(args.jobDescription)
            branchSources {
                git {
                    id(args.id)
                    credentialsId('GITHUB_SSH_KEY')
                    remote(args.gitUrl)
                    includes(args.includes)
                }
            }
            orphanedItemStrategy {
                discardOldItems {
                    numToKeep(args.discardOldNumToKeep)
                    daysToKeep(args.discardOldDaysToKeep)
                }
            }
            factory {
                workflowBranchProjectFactory {
                    scriptPath(args.jenkinsfilePath)
                }
            }
        }
    }
}
