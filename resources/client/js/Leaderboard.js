function descendingScoreOrder(s1, s2) {
    return Number(s2.UserScore) - Number(s1.UserScore);
}

function pageLoad() {

    let leaderboardHTML = `<table>` +
        '<tr>' +
        '<th>UserName</th>' +
        '<th>UserScore</th>' +
        '</tr>';

    fetch('/Users/listscores', {method: 'get'}
    ).then(response => response.json()
    ).then(scores => {

        for (let score of scores.sort(descendingScoreOrder)) {

            leaderboardHTML += `<tr>` +
                `<td>${score.UserName}</td>` +
                `<td>${score.UserScore}</td>` +
                `</tr>`;
        }

        leaderboardHTML += '</table>';

        document.getElementById("listDiv").innerHTML = leaderboardHTML;
    });
}