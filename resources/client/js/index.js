function pageLoad() {

    const canvas = document.getElementById('chartCanvas');
    const context = canvas.getContext('2d');

    let myChart = new Chart(context, {
        type: 'bar',
        data: {
            labels: [
                '1',
                '2',
                '3',
                '4',
                '5',
                '6'
            ],
            datasets: [{
                label: 'Number of things',
                data: [
                    12,
                    19,
                    3,
                    5,
                    2,
                    3
                ],
                backgroundColor: [
                    'red',
                    'red',
                    'red',
                    'red',
                    'red',
                    'red'
                ]
            }]
        },
        options: {
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }]
            },
            responsive: false
        }
    });

    checkLogin();
    let now = new Date();

    let myHTML = '<div style="text-align:center;">'
        + '<h1>Welcome To My Quiz Application!</h1>'
        + '<img src="/client/img/Logo.png"  alt="Logo"/>'
        + '<div style="font-style: italic;">'


        + 'Generated at ' + now.toLocaleTimeString()
        + '</div>'
        + '</div>';

    document.getElementById("testDiv").innerHTML = myHTML;

   document.getElementById("logoutButton").addEventListener("click", logout);
}

function checkLogin(){
    let userToken = Cookies.get("UserToken");

    if (userToken === undefined){
        window.location.href = "/client/login.html";
    }

}

function deleteuser() {

    let data = new FormData();
    data.append("UserName", Cookies.get("UserName"));

    alert("You Have Been Removed!");
// code for removing user from database
    fetch('/Users/delete', {method: 'post', body: data}
    ).then(response => response.json()
    ).then(data => {

        if (data.hasOwnProperty('error')) {
            alert(data.error);
        } else {
            window.location.href = '/client/login.html';
        }
    });
}


function logout() {

    alert("You are logged out. See you again soon!");

    fetch("/Users/logout", {method: 'post'}
    ).then(response => response.json()
    ).then(responseData => {
        if (responseData.hasOwnProperty('error')) {

            alert(responseData.error);

        } else {

            Cookies.remove("UserName");
            Cookies.remove("UserToken");

            window.location.href = '/client/login.html';

        }
    });

}
