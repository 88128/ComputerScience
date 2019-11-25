function pageLoad() {
    let LeaderboardHTML = '<table>' +
        '<tr>' +
        '<th>UserNames</th>' +
        '<th>Scores</th>' +
        // '<th class="last">Options</th>' +
        '</tr>';

    fetch('/Scores/list', {method: 'get'}
    ).then(response => response.json()
    ).then(Scores => {

        for (let TotalScore of Scores) {

            LeaderboardHTML += `<tr>` +
                `<td>${Scores.UserName}</td>` +
                `<td>${Scores.TotalScore}</td>` +
                `</td>` +
                `</tr>`;

        }
        LeaderboardHTML += '</table>';

        document.getElementById("listDiv").innerHTML = LeaderboardHTML;

    }



