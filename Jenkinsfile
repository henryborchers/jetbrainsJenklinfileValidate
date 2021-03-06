pipeline{
    agent {
        dockerfile {
            filename 'ci/docker/gradle/Dockerfile'
            label 'linux'
            additionalBuildArgs '--build-arg USER_ID=$(id -u) --build-arg GROUP_ID=$(id -g)'
            args '--mount source=gradle-cache-jenkinsfile-validate,target=/home/user/.gradle'
        }
    }
    options {
      buildDiscarder logRotator(artifactNumToKeepStr: '10')
    }
    stages{
        stage("Configure"){
            steps{
                sh label: 'Configure', script: './gradlew --info --warning-mode all'
            }
        }
        stage("Build"){
            parallel{
                stage("Build Plugin"){
                    steps{
                        sh label: 'Building plugin', script: './gradlew buildPlugin  -w --warning-mode all'
                    }
                }
                stage("Build Javadocs"){
                    steps{
                        sh label: 'Building Javadocs', script: './gradlew javadoc -w --warning-mode all '
                    }
                }
            }
        }
        stage("Run Tests"){
            steps{
                sh label: 'Running tests', script: './gradlew test -w --warning-mode all '
            }
            post{
                always{
                    junit allowEmptyResults: true, testResults: 'build/test-results/test/TEST*.xml'
                }
            }
        }
        stage("Static Analysis"){
            parallel{
                stage("Checkstyle"){
                    steps{
                        catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                            sh label: 'Running Checkstyle', script: './gradlew checkstyleMain --warning-mode all'
                        }
                    }
                    post{
                        always{
                            recordIssues(tools: [checkStyle(pattern: 'build/reports/checkstyle/*.xml')])
                        }
                    }
                }
                stage("SpotBugs"){
                    steps{
                        catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                            sh label: 'Running Spotbugs', script: './gradlew spotbugsMain --warning-mode all'
                        }
                    }
                    post{
                        always{
                            recordIssues(tools: [spotBugs(pattern: 'build/reports/spotbugs/*.xml')])
                        }
                    }
                }
            }
        }
        stage("Verify Plugin"){
            steps{
                catchError(buildResult: 'UNSTABLE') {
                    sh label: 'Verify Plugin', script: './gradlew verifyPlugin -w  --warning-mode all'
                }
            }
        }
    }
    post{
        cleanup{
            sh "./gradlew clean"
            cleanWs(
                deleteDirs: true,
                patterns: [
                    [pattern: '.gradle/', type: 'INCLUDE']
                ]
            )
        }
        success{
            archiveArtifacts 'build/distributions/*.zip,build/distributions/*.jar'
        }
    }
}
