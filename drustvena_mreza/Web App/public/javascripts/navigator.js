var globalUsername = document.getElementById('global-username').innerHTML;
//var globalUserId = document.getElementById('global-user-id').innerHTML;

function loadPartial(name, callback) {
	$.get('/partial/' + name, function(response) {
		$('#main-content').html(response);
		if (callback) {
      callback();
    }
	});
}

function loadFeed(url, element) {

	$.get(url, function(data) {
		$.each(data.contents, function() {
      if(this.content_type_id == 1) {
        this.content = BBC2HTML(this.content);
      }
			var dt = Date.parse(this.created_at);
			this.created_at = dateFormat(dt, 'dd/mm/yyyy HH:MM');
			dt = Date.parse(this.updated_at);
			this.updated_at = dateFormat(dt, 'dd/mm/yyyy HH:MM');
		});
		var templateFunction = doT.template(document.getElementById('feed-tmp').text);
		var html = templateFunction( data );
		document.getElementById(element).innerHTML = html;
		initializeVideos();
	});

}

function renderTemplate(url, template, element, callback) {
	
	$.get(url, function(data) {
		var templateFunction = doT.template(document.getElementById(template).text);
		document.getElementById(element).innerHTML = templateFunction(data);
	});
	if(callback) { callback(); }
	
}

function signOut() {
	document.getElementById('sign-out-form').submit();
}

function initialize() {
	/*
	if (document.location.pathname == '/home/homepage') {
		loadPage();
	}
	*/
	$.get('/api/content/myBubbles', function(data) {
		var templateFunction = doT.template(document.getElementById('my-bubbles-tmp').text);
		var html = templateFunction(data);
		$('#my-bubbles').html(html);
	});
	
	$('.popup-ajax').magnificPopup({
		type: 'ajax'
	});

	$('.image-popup-no-margins').magnificPopup({
		type: 'image',
		closeOnContentClick: true,
		closeBtnInside: false,
		fixedContentPos: true,
		mainClass: 'mfp-no-margins mfp-with-zoom', // class to remove default margin from left and right side
		image: {
			verticalFit: true
		},
		zoom: {
			enabled: true,
			duration: 300 // don't foget to change the duration also in CSS
		}
	});
}

function clearSearchResults() {
	var sr = document.getElementById('search-results');
	sr.innerHTML = '';
	sr.style='display:none';
}

$('#search-form').on('submit', function(event){
	event.preventDefault();
	
	var st = $('#search-term');
	if (st.val() == '') {
		st.focus();
	}
	else {
		var sr = $('#search-results');
		var sb = $('#search-form button span.glyphicon');
		sb.removeClass('glyphicon-search');
		sb.addClass('glyphicon-refresh');
		sb.addClass('gly-spin');
		
		$.get('/api/search?query='+ st.val(), function (data, status) {
			sr.html('<li class="dropdown-header"><span class="glyphicon glyphicon-user"> Contacts<li><li role="separator" class="divider"></li>');
			$.each(data.users, function() {
				itemLi = document.createElement('li');
				itemA = document.createElement('a');
				itemA.href = '/profile/view/'+ this.username;
				itemA.innerHTML = this.first_name +' '+ this.last_name;
				itemLi.appendChild(itemA);
				sr.append(itemLi);
			});
				
			sb.removeClass('gly-spin');
			sb.removeClass('glyphicon-refresh');
			sb.addClass('glyphicon glyphicon-search');
			
			$('#search-results').css('display', 'block');
			
			sb.on('click', clearSearchResults);
		});
	}
});

// hide search results when click outside happens
$(document).click(function(event) { 
    if(!$(event.target).closest('#search-results').length) {
        if($('#search-results').css('display') == 'block') {
            $('#search-results').css('display', 'none');
        }
    }        
})

function toggleOpinion(opinion, content_id) {
	$.post('/api/content/' + opinion + '/' + content_id, function() {
    var postIdn = '#post-' + content_id;
		var btn = $(postIdn + ' .actions .' + opinion);
		
		var statsIdn = postIdn + ' > .header > .stats';
		var karma = $(statsIdn + ' > .karma');
		
		if(btn.hasClass('active')) {
			btn.removeClass('active');
			
      if(opinion == 'like') {
        var likes = $(statsIdn + ' > .likes')
        likes.html(parseInt(likes.html()) - 1);
        karma.html(parseInt(karma.html()) - 1);
      }
      else if(opinion == 'dislike') {
        var dislikes = $(statsIdn + ' > .dislikes');
        dislikes.html(parseInt(dislikes.html()) - 1);
        karma.html(parseInt(karma.html()) + 1);
      }
		}
		else {
			btn.addClass('active');
			
      if(opinion == 'like') {
        var likes = $(statsIdn + ' > .likes')
        likes.html(parseInt(likes.html()) + 1);
        karma.html(parseInt(karma.html()) + 1);
      }
      else if(opinion == 'dislike') {
        var dislikes = $(statsIdn + ' > .dislikes');
        dislikes.html(parseInt(dislikes.html()) + 1);
        karma.html(parseInt(karma.html()) - 1);
      }
		}
      
	});
}

function loadComments(content_id) {
	if (content_id) {
		$('#post-' + content_id + ' .actions .comment').addClass('active');
		var cmtbtn = $('#post-' + content_id + ' .actions .comment span');
		cmtbtn.removeClass('glyphicon-comment');
		cmtbtn.addClass('gly-spin glyphicon-refresh');

		$.get('/api/content/comments/' + content_id, function (data) {
			if (data.comments.length) {
				var templateFunction = doT.template(document.getElementById('comments-tmp').text);
				$.each(data.comments, function() {
					var dt = Date.parse(this.created_at);
					this.created_at = dateFormat(dt, 'dd/mm/yyyy HH:MM');
					dt = Date.parse(this.updated_at);
					this.updated_at = dateFormat(dt, 'dd/mm/yyyy HH:MM');
				});
				var html = templateFunction(data);
				$('#post-' + content_id + ' .comments ul').html(html);
			}
			else {
				$('#post-' + content_id + ' .comments ul').html('<li>No comments</li>');
			}
			
			$('#post-' + content_id + ' .comments').slideDown();
			
			cmtbtn.removeClass('gly-spin glyphicon-refresh');
			cmtbtn.addClass('glyphicon-comment');
		});
	}
}

function postComment(content_id) {
	
	var comment = $('#post-' + content_id + ' > .comments > div > textarea');
	if(comment.val().length) {
		var pbtn = $('#post-' + content_id + ' > .comments > div > button');
		pbtn.attr('disabled', 'disabled');
		pbtn.html('Posting... <span class="glyphicon glyphicon-refresh gly-spin"></span>');
		
		$.post('/api/content/comment/' + content_id, {content_id: content_id, comment: comment.val()}, function (data) {
		
		pbtn.html('Post');
		pbtn.removeAttr('disabled');
		loadComments(content_id);
		comment.val('');
		});
	}
	else {
		comment.focus();
	}
}

function editComment(comment_id) {
	var commentIdn = '#comment-' + comment_id;
	
	if($(commentIdn).children().length == 0) {
		var commentTxt = $(commentIdn).html();
		$(commentIdn).html('<textarea>' + commentTxt + '</textarea>');
		var editor = $(commentIdn + ' textarea');
		editor.select();
		$(commentIdn).keypress(function(e) {
			if(e.which == 13) {
				var newCommentTxt = editor.val();
				$.post('/api/comment/edit/' + comment_id, {comment: newCommentTxt }, function() {
					$(commentIdn).html(newCommentTxt);
				});
			}
		});
	}
	else {
		$(commentIdn + ' > textarea').select();
	}
}

function removeComment(comment_id) {
	$.post('/api/comment/delete/' + comment_id, function() {
		$('#comment-' + comment_id).remove();
	});
}


function initializeVideos() {

	$(document).ready(function() {
        $('#main-content .popup-youtube, #main-content .popup-vimeo, #main-content .popup-gmaps').magnificPopup({
          disableOn: 700,
          type: 'iframe',
          mainClass: 'mfp-fade',
          removalDelay: 160,
          preloader: false,

          fixedContentPos: false
        });
    });
}

page('/homepage', function(){
	loadFeed('/api/home/feed', 'main-content');
});

page('/bubble/:id/', function(context) {
  loadFeed('/api/bubble/' + context.params.id + '/content', 'main-content');
});

page('/image/edit/:id', function(context) {
  loadPartial('edit-image?imageID=' + context.params.id);
});

page('/image/edit/', function(context) {
	loadPartial('edit-image/');
});

page('/image/edit/:id', function(context) {
  loadPartial('edit-image/' + context.params.id);
});

page('/new/:type', function(context){
	var type = context.params.type;
	loadPartial('new-content', function() {
		$('#new-content-category-' + type).attr('checked', 'checked');
	});
});

page('/profile/view', function() {
	loadPartial('view-profile');
});

page('/profile/edit', function() {
	loadPartial('edit-profile');
});

page('/account/manage', function() {
	loadPartial('manage-account');
});

page('/sign-out', function() {
	signOut();
});

page('/*', function(){
  loadPartial('404');
});

page.start();

$(document).ready(initialize);
