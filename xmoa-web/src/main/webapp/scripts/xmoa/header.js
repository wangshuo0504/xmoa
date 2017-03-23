var header={};
  header.page = 0;
  header.pages = ($('.head-nav').height() / 50) - 1;

if( header.pages === 0){
    $('.head-nav-prev,.head-nav-next').hide();
}

$(document).on('click', '.head-nav-prev,.head-nav-next', function(){


    if($(this).hasClass('disabled')) return;
    if($(this).hasClass('head-nav-next')){
        header.page++;
        $('.head-nav').stop().animate({'margin-top': -50* header.page}, 200);
        if( header.page ==  header.pages){
            $(this).addClass('disabled');
            $('.head-nav-prev').removeClass('disabled');
        }else{
            $('.head-nav-prev').removeClass('disabled');
        }

    }else{
        header.page--;
        $('.head-nav').stop().animate({'margin-top': -50* header.page}, 200);
        if( header.page == 0){
            $(this).addClass('disabled');
            $('.head-nav-next').removeClass('disabled');
        }else{
            $('.head-nav-next').removeClass('disabled');
        }

    }
})

$(function(){
$('.head-nav-prev,.head-nav-next').empty();
})