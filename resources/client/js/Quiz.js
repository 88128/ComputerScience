let questionsLoaded = 0;
let questionsToBeLoaded;

function displayQuestionsIfReady(allQuestionsHTML) {

    if (questionsLoaded === questionsToBeLoaded) {

        let questions = allQuestionsHTML.join("\n");
        let submit = "<input type='submit' value='OK'>";

        document.getElementById("questionForm").innerHTML = questions + submit;
    }
}

function pageLoad() {

    let quizId = 1;

    let allQuestionsHTML = [];

    fetch('/Question/get/' + quizId, {method: 'get'}
    ).then(response => response.json()
    ).then(questions => {

        questionsToBeLoaded = questions.length;

        for (let question of questions) {

            fetch('/Answer/get/' + question.QuestionNumber, {method: 'get'}
            ).then(response => response.json()
            ).then(answers => {

                let questionHTML = '<div style="padding: 10px; border: 1px solid navy;"><h1>' + question.QuestionName + '</h1>\n';

                questionHTML += '<select id="question' + question.QuestionNumber + '" name="question' + question.QuestionNumber + '">\n';

                for (let answer of answers) {
                    questionHTML += '<option value="' + answer.AnswerID + '">' + answer.Answer + '</option>\n';
                }

                questionHTML += '</select></div>';

                allQuestionsHTML.push(questionHTML);

                questionsLoaded++;

                displayQuestionsIfReady(allQuestionsHTML);

            });

        }


    });

}