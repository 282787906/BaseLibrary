/**
 * Created by evan on 16/9/8.
 */
$(function () {
    $('.nav-toggle').click(function () {
        $('body').toggleClass('nav-open');
        $('.second-nav').hide();
        setTimeout(function () {$('.nav').toggleClass(' nav-none');}, 100);
        $('.nav ul li a ').removeClass(' transform-scale');
    });

    $('.nav-title').on('click', function () {
        $(this).children().addClass(' transform-scale');
        $(this).siblings().children().addClass(' transform-scale');
        setTimeout(function(){
            $('.second-nav').show();
            $('.second-nav li a').addClass(' second-transform-scale');
        },300);

    });

    $('.go-back-menu').on('click', function () {
        $('.second-nav ul li a ').removeClass(' second-transform-scale').addClass(' transform-scale');
        $('.second-nav').hide();
        $('.nav ul li a ').removeClass(' transform-scale');
    });

    $(".mobile-inner-header-icon").click(function(){
        $('.menu-bg').toggleClass('in menu-show');
        $(this).toggleClass("mobile-inner-header-icon-click mobile-inner-header-icon-out");
        $(".mobile-inner-nav").slideToggle(250);
    });

    $(".mobile-inner-nav a").each(function( index ) {
        $( this ).css({'animation-delay': (index/10)+'s'});
    });

});