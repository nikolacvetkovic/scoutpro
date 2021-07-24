$(window).on('load', function() {
    setListenerOnInputs();
    setListenerOnSearchResultRowsCompare();
    setColorsOnFirstPlayer();
});

function setListenerOnInputs() {
    $('#compare div input').keyup(function () {
        var playerNumber = $(this).attr('name').split('-')[3];
        var searchResults = $('#searchResultPlayer-'+playerNumber+ ' tbody').get(0);
        $(searchResults).empty();
        if(this.value.length > 2){
            $.get('/player/'+this.value+'/name', function(data){
                if(data.length > 0) {
                    data.forEach(function (player) {
                        $(searchResults).append($('<tr>').attr('id', player.id)
                            .append($('<td>').append(player.playerName))
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
function setListenerOnSearchResultRowsCompare(){
    $('#playerNamesAndInputs tbody').on('click', 'tr', function (){
        var searchResult = $(this).parent().parent();
        $(searchResult).empty();
        var playerNumber = $(searchResult).attr('id').split('-')[1];
        var playerId = $(this).attr('id');
        $('#player-name-'+playerNumber+' input').css('display', 'none');
        $.get('/player/'+playerId, function(player){
            $('#player-name-'+playerNumber+ ' h6').text(player.playerName).show();
            var playerBody = $('#player'+playerNumber+' tbody').get(0);
            $(playerBody).find('tr:nth-of-type(1) td').text(player.age);
            $(playerBody).find('tr:nth-of-type(2) td').text(player.nationality);
            $(playerBody).find('tr:nth-of-type(3) td').text(player.clubTeam);
            $(playerBody).find('tr:nth-of-type(4) td').text(player.contractUntil);
            $(playerBody).find('tr:nth-of-type(5) td').text(player.psmlTeam);
            $(playerBody).find('tr:nth-of-type(6) td').text(player.psmlValue);
            $(playerBody).find('tr:nth-of-type(7) td').text(player.pesDbPlayerName);
            $(playerBody).find('tr:nth-of-type(8) td').text(player.pesDbTeamName);
            $(playerBody).find('tr:nth-of-type(9) td').text(player.position).css('color', getColorByPosition(player.position));
            player.otherStrongPositions.forEach(function(position){
                $(playerBody).find('tr:nth-of-type(10) td').append($('<span>').append(position).css({'color': getColorByPosition(position), 'padding-right': '10px'}));
            });
            player.otherWeakPositions.forEach(function(position){
                $(playerBody).find('tr:nth-of-type(10) td').append($('<span>').append(position).css({'color': getColorByPosition(position, true), 'padding-right': '10px'}));
            });
            $(playerBody).find('tr:nth-of-type(11) td').text(player.weekCondition);
            $(playerBody).find('tr:nth-of-type(12) td').text(player.offensiveAwareness).css('color', getColorByRatings(player.offensiveAwareness));
            $(playerBody).find('tr:nth-of-type(13) td').text(player.ballControl).css('color', getColorByRatings(player.ballControl));
            $(playerBody).find('tr:nth-of-type(14) td').text(player.dribbling).css('color', getColorByRatings(player.dribbling));
            $(playerBody).find('tr:nth-of-type(15) td').text(player.tightPossession).css('color', getColorByRatings(player.tightPossession));
            $(playerBody).find('tr:nth-of-type(16) td').text(player.lowPass).css('color', getColorByRatings(player.lowPass));
            $(playerBody).find('tr:nth-of-type(17) td').text(player.loftedPass).css('color', getColorByRatings(player.loftedPass));
            $(playerBody).find('tr:nth-of-type(18) td').text(player.finishing).css('color', getColorByRatings(player.finishing));
            $(playerBody).find('tr:nth-of-type(19) td').text(player.heading).css('color', getColorByRatings(player.heading));
            $(playerBody).find('tr:nth-of-type(20) td').text(player.placeKicking).css('color', getColorByRatings(player.placeKicking));
            $(playerBody).find('tr:nth-of-type(21) td').text(player.curl).css('color', getColorByRatings(player.curl));
            $(playerBody).find('tr:nth-of-type(22) td').text(player.speed).css('color', getColorByRatings(player.speed));
            $(playerBody).find('tr:nth-of-type(23) td').text(player.acceleration).css('color', getColorByRatings(player.acceleration));
            $(playerBody).find('tr:nth-of-type(24) td').text(player.kickingPower).css('color', getColorByRatings(player.kickingPower));
            $(playerBody).find('tr:nth-of-type(25) td').text(player.jump).css('color', getColorByRatings(player.jump));
            $(playerBody).find('tr:nth-of-type(26) td').text(player.physicalContact).css('color', getColorByRatings(player.physicalContact));
            $(playerBody).find('tr:nth-of-type(27) td').text(player.balance).css('color', getColorByRatings(player.balance));
            $(playerBody).find('tr:nth-of-type(28) td').text(player.stamina).css('color', getColorByRatings(player.stamina));
            $(playerBody).find('tr:nth-of-type(29) td').text(player.defensiveAwareness).css('color', getColorByRatings(player.defensiveAwareness));
            $(playerBody).find('tr:nth-of-type(30) td').text(player.ballWinning).css('color', getColorByRatings(player.ballWinning));
            $(playerBody).find('tr:nth-of-type(31) td').text(player.aggression).css('color', getColorByRatings(player.aggression));
            $(playerBody).find('tr:nth-of-type(32) td').text(player.gkAwareness).css('color', getColorByRatings(player.gkAwareness));
            $(playerBody).find('tr:nth-of-type(33) td').text(player.gkCatching).css('color', getColorByRatings(player.gkCatching));
            $(playerBody).find('tr:nth-of-type(34) td').text(player.gkClearing).css('color', getColorByRatings(player.gkClearing));
            $(playerBody).find('tr:nth-of-type(35) td').text(player.gkReflexes).css('color', getColorByRatings(player.gkReflexes));
            $(playerBody).find('tr:nth-of-type(36) td').text(player.gkReach).css('color', getColorByRatings(player.gkReach));
            $(playerBody).find('tr:nth-of-type(37) td').text(player.weakFootUsage).css('color', getColorByRatings(player.weakFootUsage));
            $(playerBody).find('tr:nth-of-type(38) td').text(player.weakFootAccuracy).css('color', getColorByRatings(player.weakFootAccuracy));
            $(playerBody).find('tr:nth-of-type(39) td').text(player.form).css('color', getColorByRatings(player.form));
            $(playerBody).find('tr:nth-of-type(40) td').text(player.injuryResistance).css('color', getColorByRatings(player.injuryResistance));
            $(playerBody).find('tr:nth-of-type(41) td').text(player.overallRating).css('color', getColorByRatings(player.overallRating));
        });
    });
}
function setColorsOnFirstPlayer(){
    // positions
    var positionTd = $('#player1 tbody tr:nth-of-type(9) td').get(0);
    $(positionTd).css('color', getColorByPosition($(td).text()));
    $('#player1 tbody tr span').css('color', function(){
        if ($(this).attr('data-strong') == 'true') return getColorByPosition($(this).text());
        if ($(this).attr('data-weak') == 'true') return getColorByPosition($(this).text(), true);
    })
    // ratings
    var td;
    for(var i=12; i<42; i++) {
        td = $('#player1 tbody tr:nth-of-type(' + i + ') td').get(0);
        $(td).css('color', getColorByRatings(Number.parseInt($(td).text())));
    }
}
function getColorByRatings(rating){
    if(rating >= 95){
        return 'red';
    } else if(rating >= 90){
        return 'rgb(214, 40, 31)';
    } else if(rating >= 80){
        return 'orange';
    } else if(rating >= 75) {
        return 'rgb(31, 193, 58)';
    } else {
        return 'black';
    }
}
function getColorByPosition(position, isWeak){
    switch(position){
        case 'GK':
            return isWeak?'rgba(150, 138, 1, 0.6)':'rgba(150, 138, 1, 1)';
            break;
        case 'CB':
        case 'LB':
        case 'RB':
            return isWeak?'rgba(18, 37, 237, 0.6)':'rgba(18, 37, 237, 1)';
            break;
        case 'DMF':
        case 'CMF':
        case 'LMF':
        case 'RMF':
        case 'AMF':
            return isWeak?'rgba(31, 193, 58, 0.6)':'rgba(31, 193, 58, 1)';
            break;
        default:
            return isWeak?'rgba(242, 0, 0, 0.6)':'rgba(242, 0, 0, 1)';
            break;
    }
}