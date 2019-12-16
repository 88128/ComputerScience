/*-------------------------------------------------------
  A utility function to extract the query string parameters
  and return them as a map of key-value pairs
  ------------------------------------------------------*/
function getQueryStringParameters() {
    let params = [];
    let q = document.URL.split('?')[1];
    if (q !== undefined) {
        q = q.split('&');
        for (let i = 0; i < q.length; i++) {
            let bits = q[i].split('=');
            params[bits[0]] = bits[1];
        }
    }
    return params;
}

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

    function checkLogin(){
        let userToken = Cookies.get("UserToken");

        if (userToken === undefined){
            window.location.href = '/client/login.html';
        }

    }

    let qs = getQueryStringParameters();

    let quizId = qs['id'];

    let allQuestionsHTML = [];

    fetch('/Question/get/' + quizId, {method: 'get'}
    ).then(response => response.json()
    ).then(questions => {

        questionsToBeLoaded = questions.length;

        for (let question of questions) {

            fetch('/Answer/get/' + question.QuestionID, {method: 'get'}
            ).then(response => response.json()
            ).then(answers => {

                let questionHTML = '<div style="padding: 10px; border: 1px solid gray;"><h1>' + question.QuestionName + '</h1>\n';

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