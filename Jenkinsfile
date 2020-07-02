  
#!/usr/bin/env groovy

node {
    stage('git') {
        git([
                url: 'git@github.com:Uber-coffee/Mobile-app.git',
                branch: "${env.BRANCH_NAME}",
                credentialsId: "mobileapp_github_access"
        ])
    }

    stage('Test ls') {
        sh 'ls -la'
    }

    stage('Telegram test') {
        telegramSend(
                message: 'Hello there',
                chatId: -1327953165:AAGvap7IwQlP3YM7vj9PAvLRH8TFyY_reKM
        )
    }

    stage('Build and Test') {
        docker.image('gradle:4.0.0-openjdk-8').inside {
            sh 'cat /etc/*releases*'
        }
    }
}