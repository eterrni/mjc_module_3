#!groovy
pipeline {
    agent any
    stages {
        stage('Clone sources') {
            steps {
                echo 'Cloning...'
                git url: 'https://github.com/eterrni/mjc_module_3.git'
            }
        }
        stage('Build & Test') {
            steps {
                echo 'Build and test..'
                sh './gradlew clean build'
                sh './gradlew compileTestJava'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying..'
                sh 'ls'
                dir("web/build/libs") {
                    script {
                        withEnv(['JENKINS_NODE_COOKIE=dontkill']) {
                            echo '&'
                            sh "java -jar webBootJar.jar &"
                        }
                    }
                }
            }
        }
    }
}