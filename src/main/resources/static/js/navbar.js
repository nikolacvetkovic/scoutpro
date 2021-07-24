setListenerOnSearchField();
setListenerOnSearchResultRows();

function setListenerOnSearchField(){
    $('#search').keyup(function(){
        var searchResults = $('#searchResult tbody').get(0);
        $(searchResults).empty();
        if(this.value.length > 2){
            $.get('/player/'+this.value+'/name', function(data){
                if(data.length > 0) {
                    data.forEach(function (player) {
                        $(searchResults).append($('<tr>')
                            .append($('<td>').append(player.playerName).attr('data-url', '/player/' + player.id + '/show'))
                            .append($('<td>').append(player.position))
                            .append($('<td>').append(player.overallRating)));
                    });
                } else {
                    $(searchResults).append('No result');
                }
            });
        }
    });
}

function setListenerOnSearchResultRows() {
    $('#searchResult tbody').on('click', 'tr:has(td)', function () {
        window.open($(this).find('td:nth-child(1)').attr('data-url'));
    })
}