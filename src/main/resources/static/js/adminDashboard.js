$(window).on('load', function() {
    setListenersOnCheckButtons();
});

function setListenersOnCheckButtons(){
    $("button.scrape-check").on('click', function(){
        var parentDiv = $(this).parent();
        $.get($(this).attr('data'), function(data){
            if(data.status == 'SUCCESS') {
                $(parentDiv).find('span.site-name').css('background-color', 'limegreen');
                $(parentDiv).find('span.last-checked').css('background-color', 'limegreen').text(data.lastChecked);
            } else {
                $(parentDiv).find('span.site-name').css('background-color', 'red');
                $(parentDiv).find('span.last-checked').css('background-color', 'red').text(data.lastChecked);
            }
        });
    });
}