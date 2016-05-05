$(document).ready(function() {
  // when a link with class="cls" is clicked, alert the link text
  $('a.spy').click(function() {
    event.preventDefault();
    $($(this).attr('href'))[0].scrollIntoView();
    scrollBy(0, -90);
  });
});
