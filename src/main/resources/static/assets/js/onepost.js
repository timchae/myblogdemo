$(document).ready(function () {
    // HTML 문서를 로드할 때마다 실행합니다.
    let id = $('#posted').text()
    console.log(id)
    addOnePost(id)
    addComments(id)
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

function addOnePost(id) {
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

function addcommentHtml(id, contents, modifiedAt, username) {
    let temphtml = `<article class="media">
                        <figure class="media-left">
                            <p class="image is-64x64">
                                <img src="/images/usericon.png"/>
                            </p>
                        </figure>
                        <div class="media-content">
                            <div class="content">
                                <p>
                                    <strong>${username}</strong>
                                    <br>
                                <span id="id-postid-commet">
                                    ${contents}
                                </span>
            
            
                                <form id="post_edit" class="media-content hide">
                                    <div class="field">
                                        <p class="control">
                                            <textarea id="id-postid-text" class="textarea" placeholder="Add a comment..."></textarea>
                                        </p>
                                    </div>
                                    <div class="field">
                                        <p class="control">
                                            <button class="button" onclick="editCommentSubmit(${id})">Edit Comment</button>
                                        </p>
                                    </div>
                                </form>
            
                                <small><a onclick="postedit()">edit</a> · <a onclick="deleteComment(${id})">delete</a> · ${modifiedAt}</small>
                                </p>
                            </div>
            
                        </div>
                    </article>`
    $('#comments').append(temphtml)
}

function addComments(pid) {
    $('#comments').empty();
    $.ajax({
        type: 'GET',
        url: `/api/comment/${pid}`,
        success: function (response) {
            console.log(response)
            for (let i = 0; i < response.length; i++) {
                let id = response[i].id;
                let modifiedAt = response[i].modifiedAt;
                let contents = response[i].contents;
                let username = response[i].user.username;
                addcommentHtml(id, contents, modifiedAt, username);
            }
        }
    })
}


function postedit() {
    if ($('#post_edit').hasClass("hide")) {
        $('#post_edit').removeClass("hide")
        let a = $('#id-postid-commet').text()
        $('#id-postid-text').val(a);
    } else {
        $('#post_edit').addClass("hide")
        $('#id-postid-text').empty()
    }
}

function editCommentSubmit(id){

    let contents = $('#id-postid-text').val();
    let data = {'id':id , 'contents':contents}
    console.log(data)
    $.ajax({
        type: "PUT",
        url: '/api/commentedit',
        contentType: "application/json", // JSON 형식으로 전달함을 알리기
        data: JSON.stringify(data),
        success: function (response) {
            alert('메시지가 성공적으로 수정되었습니다.');
            window.location.reload();
        },error: function (response){
            if (response.responseJSON && response.responseJSON.message) {
                alert(response.responseJSON.message);
            } else {
                alert("알 수 없는 에러가 발생했습니다.");
            }
        }
    });
}