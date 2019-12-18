let correctAnswers = [];

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

function checkLogin(){
    let userToken = Cookies.get("UserToken");

    if (userToken === undefined){
        window.location.href = '/client/login.html';
    }

}

function pageLoad() {



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
                    questionHTML += '<option value=' + answer.AnswerID + '>' + answer.Answer + '</option>\n';

                    if (answer.Correct) correctAnswers.push(answer.AnswerID);

                }

                questionHTML += '</select></div>';

                allQuestionsHTML.push(questionHTML);

                questionsLoaded++;

                displayQuestionsIfReady(allQuestionsHTML);

            });

        }


    });

    document.getElementById("questionForm").addEventListener("submit", markQuiz);

}

function markQuiz(event) {

    console.log(correctAnswers);

    event.preventDefault();

    let correctCount = 0;

    for (let i = 1; i <= 10; i++) {
        let answer = document.getElementById("question" + i).value;
        console.log(answer);
        if (correctAnswers.includes(String(answer))) correctCount++;
    }

    let data = new FormData();
    data.append("UserName", Cookies.get("UserName"));
    data.append("ScoreIncrease", correctCount);

    fetch('/Users/incrementscore/', {method: 'post', body: data}
    ).then(raw => raw.json()
    ).then(response => {
        alert("You got " + correctCount + " out of 10! Your new score is " + response.newscore);
        window.location.href = "/client/index.html";
    });

}