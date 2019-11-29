function pageLoad() {

    let QuizSelectionHTML = `<table>` +
        '<tr>' +
        '<th>Quiz Title</th>' +
        '<th>Quiz Skill Level</th>' +
        '</tr>';

    fetch('/Quiz/list', {method: 'get'}
    ).then(response => response.json()
    ).then(quizzes => {

        for (let quiz of quizzes) {

            QuizSelectionHTML += `<tr>` +
                `<td><a href="/client/Quiz.html?id=${quiz.QuizID}">${quiz.QuizTitle}</a></td>` +
                `<td>${quiz.QuizSkillLevel}</td>` +
                `</tr>`;
        }

        QuizSelectionHTML += '</table>';

        document.getElementById("listDiv").innerHTML = QuizSelectionHTML;

    });
}