$(window).on('load', function() {
    setWindowAndFont();
    $(window).resize(function(){
        setWindowAndFont();
    });
    getPlayersAndFillTable('/player/0/page');
    setListenersOnRows();
    setSortByAgeOnBadge();
    setSortByPositionOnBadge();
    setSortByOverallOnBadge();
    setSortByPsmlValueOnBadge();
    setSortByTMValueOnBadge();
    setFilterByAffiliation();
    setFilterByPosition();
    setListenersOnPages();
});

function setWindowAndFont(){
    if(window.screen.availWidth >= 1600){
        document.body.style.fontSize = '16px';
    } else {
        document.body.style.fontSize = '13px';
    }
    $('#left').css('height', window.innerHeight - 160);
    $('#left table tbody').css('height', $('#left').height()-34);
    $('#right').css('height', window.innerHeight - 145);
    $('#right table tbody').css('height', $('#right').height()-34);
}

function getPlayersAndFillTable(url){
    $('#left').css('opacity', '0.2');
    $.get(url, function(data){
        $('#tab-body').empty();
        data.players.forEach(function(player, index){
                var keys = Object.keys(player);
                var tr = document.createElement('tr');
                if(player.myPlayer) $(tr).addClass('table-success');
                $('#tab-body').append($(tr).append($('<td>').attr('id', 'id-value').append('').hide())
                                          .append($('<td>').attr('id', 'playerName').append(''))
                                          .append($('<td>').attr('id', 'age').css('width', '10%').append(''))
                                          .append($('<td>').attr('id', 'position').css('width', '11%').append(''))
                                          .append($('<td>').attr('id', 'overallRating').css('width', '10%').append(''))
                                          .append($('<td>').attr('id', 'psmlValue').css('text-align', 'right').css('padding-right', '3.5%').css('width', '20%').append(''))
                                          .append($('<td>').attr('id', 'tmCurrentValue').css('text-align', 'right').css('padding-right', '4.5%').css('width', '20%').append(''))
                                          .append($('<td>').attr('id', 'arrow').css('width', '5%').append($(' <i>').addClass(getArrowBasedOnRelation(player.psmlValue, player.tmCurrentValue)))));
                keys.forEach(function(key){
                    switch(key){
                        case 'id': $('#id-value').attr('id', 'id-'+player.id).append(player.id); break;
                        case 'playerName': $('#playerName').attr('id', key+'-'+player.id).append(player.playerName + ' ').append($('<a>').attr('href', '/player/'+ player.id +'/show').attr('target', '_blank').append($('<i>').addClass('fas fa-external-link-alt').css('color', 'black'))); break;
                        case 'age': $('#age').attr('id', key+'-'+player.id).append(player.age); break;
                        case 'position': $('#position').attr('id', key+'-'+player.id).append(player.position); break;
                        case 'overallRating': $('#overallRating').attr('id', key+'-'+player.id).append(player.overallRating); break;
                        case 'psmlValue': $('#psmlValue').attr('id', key+'-'+player.id).append(formatPlayerValue(player.psmlValue)); break;
                        case 'tmCurrentValue': $('#tmCurrentValue').attr('id', key+'-'+player.id).append(formatPlayerValue(player.tmCurrentValue) + ' '); break;
                        case 'otherStrongPositions': {
                            $(tr).append($('<td>').attr('id', key+'-'+player.id).hide());
                            player.otherStrongPositions.forEach(function(position, index){
                                $('#'+key+'-'+player.id).append($('<span> ').css('padding-right', '10px').append(position));
                            });
                        } break;
                        case 'otherWeakPositions': {
                            $(tr).append($('<td>').attr('id', key+'-'+player.id).hide());
                            player.otherWeakPositions.forEach(function(position, index){
                                $('#'+key+'-'+player.id).append($('<span> ').css('padding-right', '10px').append(position));
                            });
                        } break;
                        default: $(tr).append($('<td>').attr('id', key+'-'+player.id).append(player[key]).hide());
                    }
                });
        });
        renderPagination(data.currentPage, data.totalPages);
        $('#left').css('opacity', '1');
    });
}

function setListenersOnRows(){
    $('#left tbody').on('click', 'tr', function(){
        fillTransfermarkInfo(this);
        fillPsmlInfo(this);
        fillPesDbInfo(this);
        fillWhoScoredInfo(this);
        setBackgroundColorOnSelectedPlayer(this);
    });    
}

function fillTransfermarkInfo(selectedPlayerTr){
    $('#tm tr td:nth-of-type(2)').empty();
    $('#nationalTeam').append($(selectedPlayerTr).find('td[id*=nationalTeam]').text());
    $('#clubTeam').append($(selectedPlayerTr).find('td[id*=clubTeam]').text());
    $('#contractUntil').append($(selectedPlayerTr).find('td[id*=contractUntil]').text());
    $('#tmCurrentValue').append($(selectedPlayerTr).find('td[id*=tmCurrentValue]').text());
    $('#tmValueLastChanged').append($(selectedPlayerTr).find('td[id*=tmValueLastChanged]').text());
    $('#tmValueLastCheck').empty();
    $('#tmValueLastCheck').append('(' + $(selectedPlayerTr).find('td[id*=tmValueLastCheck]').text() + ')');
    $('#tm span.measure').attr('data-url', '/tm/scrape/light/'+$(selectedPlayerTr).find('td[id*=id]').text());
}

function fillPsmlInfo(selectedPlayerTr){
    $('#psml tr td:nth-of-type(2)').empty();
    $('#psmlTeam').append($(selectedPlayerTr).find('td[id*=psmlTeam]').text());
    $('#psmlValue').append($(selectedPlayerTr).find('td[id*=psmlValue]').text());
    $('#psmlLastTransferFromTeam').append($(selectedPlayerTr).find('td[id*=psmlLastTransferFromTeam]').text());
    $('#psmlLastTransferToTeam').append($(selectedPlayerTr).find('td[id*=psmlLastTransferToTeam]').text());
    $('#psmlLastTransferFee').append(formatPlayerValue($(selectedPlayerTr).find('td[id*=psmlLastTransferFee]').text()));
    $('#psmlLastTransferDate').append($(selectedPlayerTr).find('td[id*=psmlLastTransferDate]').text());
    var psmlLastCheck = $('#psmlLastCheck').get(0);
    $(psmlLastCheck).empty();
    $(psmlLastCheck).append('(' + $(selectedPlayerTr).find('td[id*=psmlLastCheck]').text() + ')');
    $('#psml span.measure').attr('data-url', '/psml/scrape/light/'+$(selectedPlayerTr).find('td[id*=id]').text());
}

function fillPesDbInfo(selectedPlayerTr){
    $('#pesDb tr td:nth-of-type(2)').empty();
    $('#pesDbPlayerName').append($(selectedPlayerTr).find('td[id*=pesDbPlayerName]').text());
    $('#pesDbTeamName').append($(selectedPlayerTr).find('td[id*=pesDbTeamName]').text());
    $('#foot').append($(selectedPlayerTr).find('td[id*=foot]').text());
    $('#weekCondition').append($(selectedPlayerTr).find('td[id*=weekCondition]').text());
    $('#primaryPosition').append($(selectedPlayerTr).find('td[id*=position]').text());
    $('#otherStrongPositions').append($(selectedPlayerTr).find('td[id*=otherStrongPositions]').html());
    $('#otherWeakPositions').append($(selectedPlayerTr).find('td[id*=otherWeakPositions]').html());
    $('#offensiveAwareness').append($(selectedPlayerTr).find('td[id*=offensiveAwareness]').text());
    $('#ballControl').append($(selectedPlayerTr).find('td[id*=ballControl]').text());
    $('#dribbling').append($(selectedPlayerTr).find('td[id*=dribbling]').text());
    $('#tightPossession').append($(selectedPlayerTr).find('td[id*=tightPossession]').text());
    $('#lowPass').append($(selectedPlayerTr).find('td[id*=lowPass]').text());
    $('#loftedPass').append($(selectedPlayerTr).find('td[id*=loftedPass]').text());
    $('#finishing').append($(selectedPlayerTr).find('td[id*=finishing]').text());
    $('#heading').append($(selectedPlayerTr).find('td[id*=heading]').text());
    $('#placeKicking').append($(selectedPlayerTr).find('td[id*=placeKicking]').text());
    $('#curl').append($(selectedPlayerTr).find('td[id*=curl]').text());
    $('#speed').append($(selectedPlayerTr).find('td[id*=speed]').text());
    $('#acceleration').append($(selectedPlayerTr).find('td[id*=acceleration]').text());
    $('#kickingPower').append($(selectedPlayerTr).find('td[id*=kickingPower]').text());
    $('#jump').append($(selectedPlayerTr).find('td[id*=jump]').text());
    $('#physicalContact').append($(selectedPlayerTr).find('td[id*=physicalContact]').text());
    $('#balance').append($(selectedPlayerTr).find('td[id*=balance]').text());
    $('#stamina').append($(selectedPlayerTr).find('td[id*=stamina]').text());
    $('#defensiveAwareness').append($(selectedPlayerTr).find('td[id*=defensiveAwareness]').text());
    $('#ballWinning').append($(selectedPlayerTr).find('td[id*=ballWinning]').text());
    $('#aggression').append($(selectedPlayerTr).find('td[id*=aggression]').text());
    $('#gkAwareness').append($(selectedPlayerTr).find('td[id*=gkAwareness]').text());
    $('#gkCatching').append($(selectedPlayerTr).find('td[id*=gkCatching]').text());
    $('#gkClearing').append($(selectedPlayerTr).find('td[id*=gkClearing]').text());
    $('#gkReflexes').append($(selectedPlayerTr).find('td[id*=gkReflexes]').text());
    $('#gkReach').append($(selectedPlayerTr).find('td[id*=gkReach]').text());
    $('#weakFootUsage').append($(selectedPlayerTr).find('td[id*=weakFootUsage]').text());
    $('#weakFootAccuracy').append($(selectedPlayerTr).find('td[id*=weakFootAccuracy]').text());
    $('#form').append($(selectedPlayerTr).find('td[id*=form]').text());
    $('#injuryResistance').append($(selectedPlayerTr).find('td[id*=injuryResistance]').text());
    $('#overallRating').append($(selectedPlayerTr).find('td[id*=overallRating]').text());
    $('#pesDbLastCheck').empty();
    $('#pesDbLastCheck').append('(' + $(selectedPlayerTr).find('td[id*=pesDbLastCheck]').text() + ')');
    $('#pesDb span.measure').attr('data-url', '/pesdb/scrape/'+$(selectedPlayerTr).find('td[id*=id]').text());
    setColorOnRatings();
    setColorOnPositions();
}

function fillWhoScoredInfo(selectedPlayerTr){
    $('#ws tr td').empty();
    $('#totalStartedApps').append($(selectedPlayerTr).find('td[id*=totalStartedApps]').text());
    $('#totalMins').append($(selectedPlayerTr).find('td[id*=totalMins]').text());
    $('#totalGoals').append($(selectedPlayerTr).find('td[id*=totalGoals]').text());
    $('#totalAssists').append($(selectedPlayerTr).find('td[id*=totalAssists]').text());
    $('#averageShotsPerGame').append($(selectedPlayerTr).find('td[id*=averageShotsPerGame]').text());
    $('#averagePassSuccess').append($(selectedPlayerTr).find('td[id*=averagePassSuccess]').text());
    $('#averageAerialsWon').append($(selectedPlayerTr).find('td[id*=averageAerialsWon]').text());
    $('#totalManOfTheMatch').append($(selectedPlayerTr).find('td[id*=totalManOfTheMatch]').text());
    $('#averageRating').append($(selectedPlayerTr).find('td[id*=averageRating]').text());
    $('#statisticsLastCheck').empty();
    $('#statisticsLastCheck').append('(' + $(selectedPlayerTr).find('td[id*=statisticsLastCheck]').text() + ')');
    $('#ws span.measure').attr('data-url', '/ws/scrape/light/'+$(selectedPlayerTr).find('td[id*=id]').text());
}

function setColorOnRatings(){
    var ratings = document.querySelectorAll('#pesDb table tr td:nth-of-type(2)');
    ratings.forEach(function(rating){
        rating.style.color = 'black';
        if(!isNaN(rating.textContent)){
            if(rating.textContent >= 95){
                rating.style.color = 'red';
            } else if(rating.textContent >= 90){
                rating.style.color = 'rgb(214, 40, 31)';
            } else if(rating.textContent >= 80){
                rating.style.color = 'orange';
            } else if(rating.textContent >= 75){
                rating.style.color = 'rgb(31, 193, 58)';
            }
        }
    });
}

function setColorOnPositions(){
    $('#primaryPosition').css('color', getColorBasedOnPosition($('#primaryPosition').html()));
    $('#otherStrongPositions').get().forEach(function(span){
        $(span).css('color', getColorBasedOnPosition($(span).text()));
    });
    $('#otherWeakPositions span').get().forEach(function(span){
        $(span).css('color', getColorBasedOnPosition($(span).text(), true));
    });
}

function setBackgroundColorOnSelectedPlayer(selectedPlayer){
    $('#left tbody tr').removeClass('table-danger').attr('selected', 'false').css('font-weight', 'normal');
    $(selectedPlayer).addClass('table-danger').attr('selected', 'true').css('font-weight', 'bold');
}

function setUrlsOnMeasureBadges(selectedPlayer){
    $('#tm h6 span.badge').attr('data-url', '/player/transfermarkt/' + selectedPlayer.id);
    $('#psml h6 span.badge').attr('data-url', '/player/psml/' + selectedPlayer.id);
    $('#pesDb h6 span.badge').attr('data-url', '/player/pesdb/' + selectedPlayer.id);
    $('#ws h6 span.badge').attr('data-url', '/player/whoscored/' + selectedPlayer.id);
}

function setListenersOnBadges(){
    $('span.badge[data-url]').on('click', function(){
        var URL = $(this).attr('data-url');
        setLoadingSpinner(URL.split('/')[2]);
        if(URL !== '#'){
            $.ajax({
                url: URL,
                type: 'PUT',
                success: function(updatedPlayer){
                    var siteForUpdate = URL.split('/')[2];
                    var currentlySelectedPlayerId = parseInt($('#tab-body tr[selected="selected"] td:nth-of-type(1)').get(0).textContent);
                    switch (siteForUpdate){
                        case 'transfermarkt':
                            updateDataScript(updatedPlayer, updateDataScriptTransfermarkt);
                            $('#tab-body tr:has(td[id-value="'+updatedPlayer.id+'"]) td:last-of-type').html(formatPlayerValue(updatedPlayer.transfermarktInfo.currentValue));
                            $('#tab-body tr:has(td[id-value="'+updatedPlayer.id+'"]) td:nth-of-type(6) i').removeClass();
                            $('#tab-body tr:has(td[id-value="'+updatedPlayer.id+'"]) td:nth-of-type(6) i').addClass(getArrowBasedOnRelation(updatedPlayer.psmlInfoList[updatedPlayer.psmlInfoList.length-1].teamValue, updatedPlayer.transfermarktInfo.currentValue));
                            if(updatedPlayer.id === currentlySelectedPlayerId){
                                fillTransfermarkInfo(updatedPlayer);
                            }
                            break;
                        case 'psml':
                            updateDataScript(updatedPlayer, updateDataScriptPsml);
                            $('#tab-body tr:has(td[id-value="'+updatedPlayer.id+'"]) td:nth-of-type(6)').html(formatPlayerValue(updatedPlayer.psmlInfoList[updatedPlayer.psmlInfoList.length-1].teamValue).concat(' ').concat('<i class="'+getArrowBasedOnRelation(updatedPlayer.psmlInfoList[updatedPlayer.psmlInfoList.length-1].teamValue, updatedPlayer.transfermarktInfo.currentValue)+'" aria-hidden="true"></i>'));
                            if(updatedPlayer.id === currentlySelectedPlayerId){
                                fillPsmlInfo(updatedPlayer);
                            }
                            break;
                        case 'pesdb':
                            updateDataScript(updatedPlayer, updateDataScriptPesDb);
                            $('#tab-body tr:has(td[id-value="'+updatedPlayer.id+'"]) td:nth-of-type(4)').html(updatedPlayer.pesDbInfoList[updatedPlayer.pesDbInfoList.length-1].primaryPosition);
                            $('#tab-body tr:has(td[id-value="'+updatedPlayer.id+'"]) td:nth-of-type(5)').html(updatedPlayer.pesDbInfoList[updatedPlayer.pesDbInfoList.length-1].overallRating);
                            if(updatedPlayer.id === currentlySelectedPlayerId){
                                fillPesDbInfo(updatedPlayer);
                            }                            
                            break;
                        case 'whoscored':
                            updateDataScript(updatedPlayer, updateDataScriptWhoScored);
                            if(updatedPlayer.id === currentlySelectedPlayerId){
                                fillWhoScoredInfo(updatedPlayer);
                            }  
                            break;
                    }
                    removeLoadingSpinner();
                }
            });
        }
    });
}

function setSortByPositionOnBadge(){
    $('#position-badge-asc').on('click', function(){
        sortPlayerTableByPosition('asc');
    });

    $('#position-badge-desc').on('click', function(){
        sortPlayerTableByPosition('desc');
    });
}

function setSortByAgeOnBadge(){
    $('#age-badge-asc').on('click', function(){
        sortPlayerTableByNumber('age', 'asc');
    });
    $('#age-badge-desc').on('click', function(){
        sortPlayerTableByNumber('age', 'desc');
    });
}

function setSortByOverallOnBadge(){
    $('#overall-badge-asc').on('click', function(){
        sortPlayerTableByNumber('overall', 'asc');
    });
    $('#overall-badge-desc').on('click', function(){
        sortPlayerTableByNumber('overall', 'desc');
    });
}

function setSortByPsmlValueOnBadge(){
    $('#psmlValue-badge-asc').on('click', function(){
        sortPlayerTableByValue('psmlValue', 'asc');
    });
    $('#psmlValue-badge-desc').on('click', function(){
        sortPlayerTableByValue('psmlValue', 'desc');
    });
}

function setSortByTMValueOnBadge(){
    $('#tmCurrentValue-badge-asc').on('click', function(){
        sortPlayerTableByValue('tmCurrentValue', 'asc');
    });
    $('#tmCurrentValue-badge-desc').on('click', function(){
        sortPlayerTableByValue('tmCurrentValue', 'desc');
    });
}

function setFilterByAffiliation(){
    $('#allPlayersButton').on('click', showAllPlayers);
    $('#myPlayersButton').on('click', showMyPlayers);
    $('#freePlayersButton').on('click', showFreePlayers);
}

function setFilterByPosition(){
    $('#dashboard span[id*=position]').on('click', function (){
        if ($(this).attr('active') == 'true') {
            $('#dashboard span[id*=position]').attr('active', false);
            getPlayersAndFillTable('/player/0/page');
        } else {
            $('#dashboard span[id*=position]').attr('active', false);
            $(this).attr('active', true);
            var position = $(this).attr('id').split('-')[1];
            getPlayersAndFillTable('/player/0/page?position=' + position);
        }
    });
}

function setListenersOnPages(){
    $('#pages').on('click', "span[enabled=true], i.fas[data-page][enabled=true]", function(){
        var activePositionFilterButton = $('#dashboard span[id*=position][active=true]');
        var selectedPageNumber = $(this).attr('data-page');
        if (activePositionFilterButton.length == 0) {
            getPlayersAndFillTable('/player/' + selectedPageNumber + '/page');
        } else {
            var activePositionFilterButtonValue = $(activePositionFilterButton).attr('id').split('-')[1];
            getPlayersAndFillTable('/player/' + selectedPageNumber + '/page?position=' + activePositionFilterButtonValue);
        }
    });
}

function renderPagination(currentPage, totalPages){
    $('#pages').empty();
    var min = currentPage - 3;
    min = min<0 ? 0 : min;
    var max = currentPage + 3;
    max = max>totalPages? totalPages : max;

    $('#pages').append($('<i>').attr({'enabled':currentPage!==0?'true':'false', 'data-page':0}).addClass('fas fa-angle-double-left'));
    if(!((currentPage-1) < 0)) $('#pages').append($('<i>').attr({'enabled':'true', 'data-page':(currentPage-1)}).addClass('fas fa-chevron-circle-left'));

    for(var i=min; i<max; i++){
            $('#pages').append($('<span>').append(i+1).attr({'enabled':'true', 'data-page':(i)}).addClass('badge badge-primary'));
            if(currentPage === i)
                $('#pages span:last-child').attr('enabled', 'false');
    }

    if((currentPage+1) < totalPages) $('#pages').append($('<i>').attr({'enabled':'true', 'data-page':(currentPage+1)}).addClass('fas fa-chevron-circle-right'));
    $('#pages').append($('<i>').attr({'enabled':currentPage!==totalPages-1?'true':'false', 'data-page':(totalPages-1)}).addClass('fas fa-angle-double-right'));
}

function getArrowBasedOnRelation(psmlValue, tmValue){
    var psmlValueNumber = Number.parseFloat(psmlValue);
    var tmValueNumber = Number.parseFloat(tmValue);
    if(psmlValueNumber === 0.00 || tmValueNumber === 0.00) return '';
    if(psmlValueNumber === tmValueNumber){
        return 'fas fa-minus-square';
    } else if(psmlValueNumber > tmValueNumber){
        return 'fas fa-arrow-circle-down';
    } else {
        return 'fas fa-arrow-circle-up';
    }
}

function getColorBasedOnPosition(position, isWeak){
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

function formatPlayerValue(value){
    return '€ ' + Number.parseFloat(value).toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}

function setLoadingSpinner(site){
    switch(site){
        case 'transfermarkt':
           createSpinner('tm');
        break;
        case 'psml':
            createSpinner('psml');
        break;
        case 'pesdb':
            createSpinnerPesDb();
        break;
        case 'whoscored':
            createSpinnerWhoScored();
        break;
    }
}

function createSpinner(siteElementId){
    $('#'+siteElementId+' .row').append($('<div>').css({
        'height': '100%',
        'width': '100%',
        'position' : 'absolute',
        'z-index': 10,
        'top': 0,
        'left': '0',
        'background-color': 'rgba(150, 150, 150, 0.8)'
        }).addClass('spinner').append($('<img>').attr('src', '/Gear-3.6s-200px.gif').css({'max-height': '100%', 'max-width': '100%', 'margin-left': '30%'})));
}

function createSpinnerPesDb(){
    $('#pesDb .row').append($('<div>').css({
        'height': '100%',
        'width': '100%',
        'position' : 'absolute',
        'z-index': 10,
        'top': 0,
        'left': '0',
        'background-color': 'rgba(150, 150, 150, 0.8)'
        }).addClass('spinner').append($('<img>').attr('src', '/Gear-3.6s-200px.gif').css({'max-height': '100%', 'max-width': '100%', 'margin': '12% auto', 'display': 'block'})));
}

function createSpinnerWhoScored(){
    $('#ws .row').append($('<div>').css({
        'height': '100%',
        'width': '100%',
        'position' : 'absolute',
        'z-index': 10,
        'top': 0,
        'left': '0',
        'background-color': 'rgba(150, 150, 150, 0.8)'
        }).addClass('spinner').append($('<img>').attr('src', '/Gear-3.6s-200px.gif').css({'height': '80%', 'display': 'block', 'margin': '1em auto'})));
}

function removeLoadingSpinner(){
    $('#tm .row .spinner').remove();
    $('#psml .row .spinner').remove();
    $('#pesDb .row .spinner').remove();
    $('#ws .row .spinner').remove();
}

function showAllPlayers(){
    $('#tab-body tr').css('display', '');
}

function showMyPlayers(){
    var playerList = $('#tab-body tr').get();
    playerList.forEach(function(player){
        if($(player).find('td[id*=myPlayer]').text()){
            $(player).css('display', '');
        } else {
            $(player).css('display', 'none');
        }
    });
}

function showFreePlayers(){
    var playerList = $('#tab-body tr').get();
    playerList.forEach(function(player){
        if($(player).find('td[id*=psmlTeam]').text() === 'Free'){
            $(player).css('display', '');
        } else {
            $(player).css('display', 'none');
        }
    });
}

function sortPlayerTableByNumber(orderBy, order){
    var playerList = $('#tab-body tr').get();
    playerList.sort(function(a, b){
        var a = parseInt($(a).find('td[id*='+orderBy+']').text());
        var b = parseInt($(b).find('td[id*='+orderBy+']').text());
        if(order === 'asc'){
            return a - b;
        } else if(order === 'desc'){
            return b - a;
        }
    });
    $('#tab-body').empty();
    $('#tab-body').append(playerList);
}

function sortPlayerTableByPosition(order){
    var playerList = $('#tab-body tr').get();
    playerList.sort(function(a, b){
        var aPositionNumber = parseInt(getPositionNumber($(a).find('td[id*=position]').text()));
        var bPositionNumber = parseInt(getPositionNumber($(b).find('td[id*=position]').text()));
        if(order === 'asc'){
            return aPositionNumber - bPositionNumber;
        } else if(order === 'desc'){
            return bPositionNumber - aPositionNumber;
        }
    });
    $('#tab-body').empty();
    $('#tab-body').append(playerList);
}

function sortPlayerTableByValue(orderBy, order){
    var playerList = $('#tab-body tr').get();
        playerList.sort(function(a, b){
            var x = parseInt($(a).find('td[id*='+orderBy+']').text().replace(/[^0-9]/g, ''));
            var y = parseInt($(b).find('td[id*='+orderBy+']').text().replace(/[^0-9]/g, ''));
            if(order === 'asc'){
                if(x === y){
                    if(orderBy === 'psmlValue'){
                        var c = parseInt($(a).find('td[id*=tmCurrentValue]').text().replace(/[^0-9]/g, ''));
                        var d = parseInt($(b).find('td[id*=tmCurrentValue]').text().replace(/[^0-9]/g, ''));
                        return c - d;
                    } else if(orderBy === 'tmCurrentValue'){
                    var c = parseInt($(a).find('td[id*=psmlValue]').text().replace(/[^0-9]/g, ''));
                    var d = parseInt($(b).find('td[id*=psmlValue]').text().replace(/[^0-9]/g, ''));
                    return c - d;
                    }
                }
                return x - y;
            } else if(order === 'desc'){
                if(x === y){
                    if(orderBy === 'psmlValue'){
                        var c = parseInt($(a).find('td[id*=tmCurrentValue]').text().replace(/[^0-9]/g, ''));
                        var d = parseInt($(b).find('td[id*=tmCurrentValue]').text().replace(/[^0-9]/g, ''));
                        return d - c;
                    } else if(orderBy === 'tmCurrentValue'){
                        var c = parseInt($(a).find('td[id*=psmlValue]').text().replace(/[^0-9]/g, ''));
                        var d = parseInt($(b).find('td[id*=psmlValue]').text().replace(/[^0-9]/g, ''));
                        return d - c;
                    }
                }
                return y - x;
            }
        });
        $('#tab-body').empty();
        $('#tab-body').append(playerList);
}

function getPositionNumber(position){
    switch(position){
        case 'GK': return 1; break;
        case 'CB': return 2; break;
        case 'LB': return 3; break;
        case 'RB': return 4; break;
        case 'DMF': return 5; break;
        case 'CMF': return 6; break;
        case 'LMF': return 7; break;
        case 'RMF': return 8; break;
        case 'AMF': return 9; break;
        case 'LWF': return 10; break;
        case 'RWF': return 11; break;
        case 'SS': return 12; break;
        case 'CF': return 13; break;
    }
}