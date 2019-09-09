# jenkins-configuration-as-code-seed-job

Initial job that populates predefined repositories into a clean [Jenkins Configuration As Code](https://jenkins.io/projects/jcasc/) environment.

## Getting Started

Run [jenkins-configuration-as-code](https://github.com/sheeeng/jenkins-configuration-as-code/) demonstration project.

## Update Seed Job Script

- Modify [build.groovy](build.groovy) file. Commit it and push to remote upstream.
- Run [jenkins-configuration-as-code](https://github.com/sheeeng/jenkins-configuration-as-code/) demonstration project. Log in to Jenkins.
- Click `Manage Jenkins`. Scroll down to view `In-Process Script Approval` section. If approval needed, review it and approve when it is verified.
- Execute into the running [jenkins-configuration-as-code](https://github.com/sheeeng/jenkins-configuration-as-code/) container.
- Copy the updated signature checksum hash from the `scriptApproval.xml` inside the Jenkins prime container.

    ```bash
    sed --quiet '/string/{s/.*<string>//;s/<\/string.*//;p;}' /var/jenkins_home/scriptApproval.xml
    ```

- Paste the updated signature checksum hash into the copy of [scriptApproval.xml](https://github.com/sheeeng/jenkins-configuration-as-code/blob/master/prime/scriptApproval.xml) file after the [build.groovy](build.groovy) file in this project was modified.
- Run [jenkins-configuration-as-code](https://github.com/sheeeng/jenkins-configuration-as-code/) again.
- Verify that the seed job itself has build successfully.
- Verify the other predefined jobs has at least one default branches available for build, as the multibranch scan should have succeeded.
