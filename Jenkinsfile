pipeline{
    agent {
        docker {
            image 'gradle'
            label 'linux'
        }
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
                        sh label: 'Building plugin', script: './gradlew buildPlugin  -w --warning-mode all | tee gradle.build.log'
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
                sh label: 'Get Coverage Report', script: './gradlew jacocoTestReport -w --warning-mode all '
            }
            post{
                always{
                    junit 'build/test-results/test/TEST*.xml'
                    publishCoverage adapters: [jacocoAdapter('build/reports/jacoco/test/jacocoTestReport.xml')]
//                     archiveArtifacts 'build/reports/jacoco/test/jacocoTestReport.xml'
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
        always{
            sh "./gradlew clean"
        }
    }
}