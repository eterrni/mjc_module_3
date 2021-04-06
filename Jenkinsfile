#!groovy
pipeline {
    agent any
    stages {

        stage("Get code") {
            steps {
                git branch: 'develop', changelog: true, poll: true, url: 'https://github.com/eterrni/mjc_module_3.git/'
            }
        }

        stage("Build & Test") {
            steps {
                echo 'Build and Test ...'
                bat './gradlew clean build codeCoverageReport'
            }
        }

        stage("SonarQube") {
            environment {
                scannerHome = tool 'sonarqube'
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat "\"${scannerHome}\\bin\\sonar-scanner.bat\""
                }
            }
        }

        stage("Deploy") {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'tomcat_credentials',
                        path: '', url: 'http://localhost:8088/')],
                        contextPath: '', onFailure: false, war: '**/*.war'
            }
        }
    }
}