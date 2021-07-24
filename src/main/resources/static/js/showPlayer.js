$(window).on('load', function() {
    setColorOnRatings();
    setColorOnPositions();
    setCharacteristics();
    setCharts();
    setGameStats();
    setListenerOnMyPlayerCheckBox();
});

function setColorOnRatings(){
    $('#pesDb table tr td:nth-of-type(2)').get().forEach(function(rating){
        $(rating).css('color', getColorBasedOnRating($(rating).text()));
    });
    var mainOverall = $('#mainOverall').get();
    $(mainOverall).css('color', getColorBasedOnRating($(mainOverall).text()));
}

function setColorOnPositions(){
    $('#primaryPosition').css('color', getColorBasedOnPosition($('#primaryPosition').html()));
    $('#otherStrongPositions').get().forEach(function(span){
        $(span).css('color', getColorBasedOnPosition($(span).text()));
    });
    $('#otherWeakPositions span').get().forEach(function(span){
        $(span).css('color', getColorBasedOnPosition($(span).text(), true));
    });
    var mainPosition = $('#mainPosition').get();
    $(mainPosition).css('color', getColorBasedOnPosition($(mainPosition).text()));
}

function setCharacteristics(){
    var strengths = $('#characteristics #strengths div:not(:first-child)').get();

    strengths.forEach(function(strength){
    	var arr = $(strength).text().split(' - ');
        switch (arr[1]) {
    		case 'Strong': $(strength).html('<i class="fas fa-angle-right"></i> ' + arr[0]).css('color', 'green'); break;
            case 'Very Strong': $(strength).html('<i class="fas fa-angle-right"></i>' + arr[0]).css('color', 'green').css('font-weight', '900'); break;
        }
    });

    var weaknesses = $('#characteristics #weaknesses div:not(:first-child)').get();

    weaknesses.forEach(function(weakness){
    	var arr = $(weakness).text().split(' - ');
        switch (arr[1]) {
    		case 'Weak': $(weakness).html('<i class="fas fa-angle-right"></i> ' + arr[0]).css('color', 'red'); break;
            case 'Very Weak': $(weakness).html('<i class="fas fa-angle-right"></i> ' + arr[0]).css('color', 'red').css('font-weight', '900'); break;
        }
    });

    var styles = $('#characteristics #styleOfPlay div:not(:first-child)').get();

    styles.forEach(function(style){
        $(style).html('<i class="fas fa-angle-right"></i> ' + $(style).text()).css('color', 'deeppink');
    });
}

function setCharts(){
    var ctx = $('#marketValuesChart')[0].getContext('2d');
    var marketValues = $('#marketValuesData div').get();
    var datePoints = [];
    var values = [];
    var clubTeams = [];
    marketValues.forEach(function(mv){
        datePoints.push($(mv).find('span:nth-of-type(1)').text());
        values.push($(mv).find('span:nth-of-type(2)').text());
        clubTeams.push($(mv).find('span:nth-of-type(3)').text());
    });
    var marketValuesChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: datePoints,
            datasets: [{
                           label: 'Market Value',
                           backgroundColor: '#007bff',
                           borderColor: 'rgb(255, 99, 132)',
                           data: values
                       }]
        },
        options: {
//            title:{
//                display: true,
//                text: 'Market Values',
//                fontSize: 16,
//                fontFamily: '-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Noto Sans",sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji"'
//            },
            scales:{
                yAxes:[{
                    gridLines:{
                        display: false
                    },
                    ticks:{
                        callback: function(value, index, values){
                            return formatPlayerValue(value);
                        }
                    }
                }],
                xAxes:[{
                    gridLines:{
                        display: false
                    }
                }]
            },
            legend:{
                display: false
            },
            tooltips:{
                displayColors: false,
                callbacks: {
                    title: function(tooltipItems, data){
                        return clubTeams[tooltipItems[0].index];
                    },
                    label: function(tooltipItem, data){
                        return formatPlayerValue(tooltipItem.value);
                    }
                }
            }
        }
    });
}

function setGameStats(){
    var motms = $('#gameStats td[data-attribute=motm]').get();
    motms.forEach(function(td){
        if($(td).text()==='true'){
            $(td).html('<i class="fas fa-star"></i>');
        } else {
            $(td).html('');
        }
    });

    var goals = $('#gameStats td[data-attribute=goals]').get();
    goals.forEach(function(td){
    	var g = Number.parseInt($(td).text());
    	var html = '';
    	for(var i=0; i<g; i++){
    		html+='<i class="far fa-futbol"></i>';
    	}
    	$(td).html(html);
    });

    var assists = $('#gameStats td[data-attribute=assists]').get();
    assists.forEach(function(td){
        var a = Number.parseInt($(td).text());
        var html = '';
        for(var i=0; i<a; i++){
            html+='<i class="fab fa-adn"></i>';
        }
        $(td).html(html);
    });

    var yellowCards = $('#gameStats td[data-attribute=yellow]').get();
    yellowCards.forEach(function(td){
    	if($(td).text()==='true'){
    	    $(td).html('<i class="fas fa-square"></i>');
    	} else {
    	    $(td).html('');
    	}
    });

    var redCards = $('#gameStats td[data-attribute=red]').get();
    redCards.forEach(function(td){
        if($(td).text()==='true'){
            $(td).html('<i class="fas fa-square"></i>');
        } else {
            $(td).html('');
        }
    });

    var minutesPlayed = $('#gameStats td[data-attribute=minutesPlayed]').get();
    minutesPlayed.forEach(function(m){
        $(m).html($(m).text()+'"');
    });

}

function setListenerOnMyPlayerCheckBox(){
    $('#myPlayer').click(function (){
        var followButton = $('#followPlayer').get(0);
        var followButtonHrefValue = $(followButton).attr('href');
        if(followButtonHrefValue.includes('false')) {
            followButtonHrefValue = followButtonHrefValue.replace('false', 'true');
        } else if($(followButton).attr('href').includes('true')) {
            followButtonHrefValue = followButtonHrefValue.replace('true', 'false');
        }
        $(followButton).attr('href', followButtonHrefValue);
    });
}

function formatPlayerValue(value){
    return '€ ' + Number.parseFloat(value).toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
}

function getColorBasedOnRating(rating){
    if(!isNaN(rating)){
        if((rating) >= 95){
            return 'red';
        } else if(rating >= 90){
            return 'rgb(214, 40, 31)';
        } else if(rating >= 80){
            return 'orange';
        } else if(rating >= 75){
            return 'rgb(31, 193, 58)';
        }
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