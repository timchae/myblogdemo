$(document).ready(function () {
    // HTML 문서를 로드할 때마다 실행합니다.
    let id = $('#posted').text()
    console.log(id)
    addOnePost(id)
})

function addOneHtml(id, title, contents, modifiedAt, url, username) {

    let temphtml = `<!-- Post -->
                    <article class="post">
                        <header>
                            <div class="title">
                                <h2><a href="#">${title}</a></h2>
                                <p>Lorem ipsum dolor amet nullam consequat etiam feugiat</p>
                            </div>
                            <div class="meta">
                                <time class="published" datetime="2015-11-01">${modifiedAt}</time>
                                <a href="#" class="author"><span class="name">${username}</span><img src="/images/avatar.jpg"
                                                                                                  alt=""/></a>
                            </div>
                        </header>
                        <span class="image featured"><img src="${url}" alt=""/></span>
                        ${contents}
                        <footer>
                            <ul class="stats">
                                <li><a href="#">General</a></li>
                                <li><a href="#" class="icon solid fa-heart">28</a></li>
                                <li><a href="#" class="icon solid fa-comment">128</a></li>
                            </ul>
                        </footer>
                    </article>`

    $('#main').append(temphtml);

}

function addOnePost(id){
    $('#main').empty();
    $.ajax({
        type: 'GET',
        url: `/api/post/${id}`,
        success: function (response) {

            console.log(response)
            let id = response.id;
            let title = response.title;
            let contents = response.contents;
            let modifiedAt = response.modifiedAt;
            let url = response.url;
            let username = response.username;

            addOneHtml(id, title, contents, modifiedAt, url, username);

        }
    })

}

function editpost(){


}