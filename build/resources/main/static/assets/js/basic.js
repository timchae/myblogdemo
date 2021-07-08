let temp_id;
$(document).ready(function () {
    // HTML 문서를 로드할 때마다 실행합니다.
    getPost();

})


function deletePost(id){
    $.ajax({
        type: "DELETE",
        url: `/api/post/${id}`,
        success: function (response) {
            alert('포스트 삭제에 성공하였습니다.');
            window.location.reload();
        }
    })
}
function editsave(){

    let title = $('#titlee').val();
    let content = $('#contentse').val();
    let name = $('#namee').val();
    let url = $('#img_urle').val();

    if(url == ""){
        url = "images/tim.jpeg"
    }

    if(isValidContents(title) == false){
        alert('제목을 입력해주세요');
        return;
    }
    if(isValidContents(content) == false){
        alert('내용을 입력해주세요');
        return;
    }
    if(isValidContents(name) == false){
        alert('이름을 입력해주세요');
        return;
    }
    let data = {'name': name,'title':title, 'contents': content, 'url': url}
    $.ajax({
        type: "PUT",
        url: `/api/post/${temp_id}`,
        contentType: "application/json", // JSON 형식으로 전달함을 알리기
        data: JSON.stringify(data),
        success: function (response) {
            alert('메시지가 성공적으로 수정되었습니다.');
            window.location.reload();
        }
    });
}
function editPost(id){
    temp_id = id;
    console.log(id);
    $('#postingedit').addClass('is-active');
}

function posth(id){
    $.ajax({
        type: 'GET',
        url: `/api/post/${id}`,
        success: function (response) {

            let id = response.id;
            let name = response.name;
            let title = response.title;
            let contents = response.contents;
            let modifiedAt = response.modifiedAt;
            let url = response.url;
            addOnePost(id, name, title, contents, modifiedAt, url)

            console.log(response)
        }
    })
    $('#post-cards').hide();
    $('#one_post').show();
}

function addHTML(id, title, contents, modifiedAt, url, username) {
    console.log(url)
    let temphtml = `<article class="post">
                        <header>
                            <div class="title">
                                <h2><a href="/post/one/${id}" >${title}</a></h2>                            
                            </div>
                            <div class="meta">
                                <time class="published" dateTime="2015-11-01">${modifiedAt}</time>
                                <a href="#" class="author"><span class="name">${username}</span><img src="images/usericon.png"
                                                                                                          alt=""/></a>
                            </div>
                        </header>
                        <a href="/post/one/${id}" class="image featured"><img src="${url}" alt=""/></a>
                        <p>${contents}</p>
                        <footer>
                            <ul class="actions">
                                <li><a href="/post/one/${id}" class="button large">Continue Reading</a></li>
                            </ul>
                            <ul class="stats">
                                <li><a href="#">General</a></li>
                                <li><a href="#" class="icon solid fa-heart">28</a></li>
                                <li><a href="#" class="icon solid fa-comment">128</a></li>
                            </ul>
                        </footer>
                    </article>`

    $('#main').append(temphtml);
}

function getPost() {
    $("#main").empty();

    $.ajax({
        type: 'GET',
        url: '/api/post',
        success: function (response) {

            for(let i = 0 ;i < response.length; i++){
                console.log(response)
                let id = response[i].id;
                let title = response[i].title;
                let contents = response[i].contents;
                let modifiedAt = response[i].modifiedAt;
                let url = response[i].url;
                let username = response[i].username;
                if(contents.length > 100){
                    contents = contents.substring(0,100);
                    contents = contents + '  . . .';
                }
                addHTML(id,title, contents, modifiedAt, url, username)
            }
        }
    })
}

function isValidContents(contents) {
    if (contents == '') {
        return false;
    }
    if (contents.trim().length > 1000) {
        alert('공백 포함 140자 이하로 입력해주세요');
        return false;
    }
    return true;
}


// ----------------------------------------- MY BLOG --------------------------------------------
// ----------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------

