$(document).ready(function () {
  markActiveMenuItem();
});

$(document).ready(function () {
const links = document.querySelectorAll('.nav-link')

links.forEach(link => {
  link.addEventListener('click', (e) => {
    links.forEach(link =>  link.classList.remove('active'))
    e.target.classList.add('active')
  })
})
});


$(window).scroll(function(){
 $('nav').toggleClass('scrolled', $(this).scrollTop() > 100);
 });

function markActiveMenuItem() {
  var path = window.location.pathname;
  if (path != "/building" && !path.startsWith("/watch")) {
    $(".nav-link").each(function () {
      var href = $(this).attr("href");
      if (path.substring(0, href.length) === href) {
        $(this).addClass("active");
      }
    });
    $(".dropdown-item").each(function () {
      var href = $(this).attr("href");
      if (path.substring(0, href.length) === href) {
        $(this).closest(".nav-item").children("a").addClass("active");
      }
    });
  }
}
