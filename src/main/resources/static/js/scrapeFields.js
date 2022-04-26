$(window).on('load', function() {
    setListenersOnDropdown();
    setListenerOnButtons();
});

function setListenersOnDropdown(){
    $('button.dropdown-item').on('click', function(){
        $('#scrapeFields').empty();
        $('#icons').empty();
        var buttonValue = $(this).text();
        var scrapeSiteId = $(this).attr('data');
        $.get('/scrape/field/' + scrapeSiteId + '/site', function(data){
            $('#dropdownMenuButton').text(buttonValue);
            data.forEach(function(scrapeField, index){

                createScrapeField($('#scrapeFields'), scrapeField.id, scrapeField.name, scrapeField.selector, index);
            });
        });
    });
}

function createScrapeField(parentElement, fieldId, fieldName, fieldSelector, index){
    var span = $('<span>').addClass('input-group-text').append(fieldName);
    var divLeft = $('<div>').addClass('input-group-prepend').append(span);
    var input = $('<input>').addClass('form-control').attr('type', 'text')
                                                     .val(fieldSelector);
    var button = $('<button>').addClass('btn btn-outline-secondary').attr('type', 'button')
                                                                    .append('Submit');
    var successIcon = $('<i>').addClass('fas fa-check-circle').css({"visibility": "hidden"});
    var failIcon = $('<i>').addClass('fas fa-times-circle').css({"visibility": "hidden"});
    var divRight = $('<div>').addClass('input-group-append').append(button)
                                                            .append(successIcon)
                                                            .append(failIcon);
    var divWrapper = $('<div>').addClass('input-group input-group-sm mb-3').append(divLeft)
                                                                           .append(input)
                                                                           .append(divRight);
    var divScrapeField = $('<div>').addClass('scrape-field').attr('field-id', fieldId).append(divWrapper);
    $(parentElement).append(divScrapeField);
}

function setListenerOnButtons(){
    $('#scrapeFields').on('click', 'button', function(){
        var fieldId = $(this).parent().parent().parent().attr('field-id');
        var fieldName = $(this).parent().parent().find('div.input-group-prepend span').text();
        var fieldSelector = $(this).parent().parent().find('input').val();
        var csrfToken = $("#csrf").val();
        $.ajax({
            url: '/scrape/field/' + fieldId,
            type: 'PUT',
            contentType: 'application/json',
            headers: {"X-CSRF-TOKEN": csrfToken},
            data: '{"id":"'+fieldId+'", "name":"'+fieldName+'", "selector":"'+fieldSelector+'"}',
            success: function(response){
                $('div.scrape-field[field-id='+fieldId+'] input').val(response.selector);
                $('div.scrape-field[field-id='+fieldId+'] div.input-group-append i.fa-check-circle').css({
                    visibility: "visible",
                    display: "none"
                }).fadeIn(3000);
                setTimeout(hideIcons(fieldId, 'fa-check-circle'), 4000);
            },
            error: function(response){
                $('div.scrape-field[field-id='+fieldId+'] div.input-group-append i.fa-times-circle').css({
                                    visibility: "visible",
                                    display: "none"
                                }).fadeIn(3000);
                                setTimeout(hideIcons(fieldId, 'fa-times-circle'), 4000);
            }
        });
    });
}

function hideIcons(fieldId, iconClass){
    $('div.scrape-field[field-id='+fieldId+'] div.input-group-append i.'+iconClass)
        .fadeOut(3000, function(){
            $('div.scrape-field[field-id='+fieldId+'] div.input-group-append i.'+iconClass)
                .css({
                    visibility: "hidden",
                    display: "block"
                });
        });
}