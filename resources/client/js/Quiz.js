function pageLoad() {

    fetch('/Question/get/1', {method: 'get'}
    ).then(response => response.json()
    ).then(questions => {
        for (let question of questions) {
        }


    }