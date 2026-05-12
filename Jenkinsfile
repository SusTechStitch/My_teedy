pipeline {
    agent any
    environment {
        JAVA_HOME = "/Users/jianggg/Library/Java/JavaVirtualMachines/ms-17.0.16/Contents/Home"
        PATH = "/Users/jianggg/Library/Java/JavaVirtualMachines/ms-17.0.16/Contents/Home/bin:/opt/homebrew/bin:/opt/homebrew/opt/maven/bin:${env.PATH}"
    }  

    stages {
        stage('Environment') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test -Dmaven.test.failure.ignore=true -Dtest="*,!TestPdfFormatHandler"'
            }
        }


        stage('PMD') {
            steps {
                sh 'mvn pmd:pmd -Dpmd.typeResolution=false'
            }
        }

        stage('JaCoCo') {
            steps {
                sh 'mvn org.jacoco:jacoco-maven-plugin:0.8.12:report'
            }
        }


        stage('Site') {
            steps {
                sh 'mvn site -Dpmd.typeResolution=false'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: '**/target/site/**/*.*', fingerprint: true
            archiveArtifacts artifacts: '**/target/**/*.jar', fingerprint: true
            archiveArtifacts artifacts: '**/target/**/*.war', fingerprint: true
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
