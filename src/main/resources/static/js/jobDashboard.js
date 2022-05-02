$(window).on('load', function() {
    setListenersOnButtons();
});
function setListenersOnButtons(){
    $('#runJobButton, #updateJobButton, #scheduleJobButton, #rescheduleJobButton,'+
        '#unscheduleJobButton, #pauseJobButton, #resumeJobButton').click(function(){
        var clickedButton = $(this);
        var httpMethod = $(this).attr('data-method');
        var url = $(this).attr('data-url');
        var csrfToken = $("#csrf").val();
        var jobWrapper = $(this).parent().parent().parent();
        var jobId = $(jobWrapper).attr('data-id');
        if ($(this).attr('id') == 'updateJobButton'
                                        || $(this).attr('id') == 'scheduleJobButton'
                                                || $(this).attr('id') == 'rescheduleJobButton') {
            var startTime = $('#jobStartTime-'+jobId).val();
            var endTime = $('#jobEndTime-'+jobId).val();
            var cronJob = $('input[name=jobType-'+jobId+']:checked').val()=="true"?true:false;;
            var cronExp = $('#jobCronExp-'+jobId).val();
            var repeatCount = $('#jobRepeatCount-'+jobId).val();
            var repeatInterval = $('#jobRepeatInterval-'+jobId).val();
            var customConfig = $('#jobCustomConfig-'+jobId).val();
            var jobData = JSON.stringify({"id": jobId, "startTime": startTime, "endTime": endTime, "cronJob": cronJob,
                            "cronExp": cronExp, "repeatCount": repeatCount, "repeatInterval": repeatInterval,
                            "customConfig": customConfig});
        }
        $.ajax({
            url: url,
            type: httpMethod,
            contentType: 'application/json',
            headers: {"X-CSRF-TOKEN": csrfToken},
            data: jobData,
            success: function(job){
                $(jobWrapper).find('div.alert').html($(clickedButton).attr('data-success-msg') +'. <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
                $(jobWrapper).find('div.alert').addClass('alert-success');
                $(jobWrapper).find('div.alert').fadeIn(2000);
                setTimeout(hideAlert, 4000, jobWrapper);
                // data
                if (job) {
                    $('#jobStatus-'+jobId).text(job.status);
                    $('#jobStartTime-'+jobId).val(job.startTime);
                    $('#jobEndTime-'+jobId).val(job.endTime);
                    $('#jobTypeYes-').attr('checked', job.cronJob?true:false);
                    $('#jobTypeNo-'+jobId).attr('checked', job.cronJob?false:true);
                    $('#jobCronExp-'+jobId).val(job.cronExp);
                    $('#jobRepeatCount-'+jobId).val(job.repeatCount);
                    $('#jobRepeatInterval-'+jobId).val(job.repeatInterval);
                    $('#jobCustomConfig-'+jobId).val(job.customConfig);
                    // buttons state
                    $(jobWrapper).find('#scheduleJobButton').attr('disabled', !(job.status == 'CREATED'));
                    $(jobWrapper).find('#rescheduleJobButton').attr('disabled', !(job.status == 'SCHEDULED'));
                    $(jobWrapper).find('#unscheduleJobButton').attr('disabled', !(job.status == 'SCHEDULED'));
                    $(jobWrapper).find('#pauseJobButton').attr('disabled', !(job.status == 'SCHEDULED'));
                    $(jobWrapper).find('#resumeJobButton').attr('disabled', !(job.status == 'PAUSED'));
                }
            },
            error: function(response){
                $(jobWrapper).find('div.alert').html($(clickedButton).attr('data-error-msg') +'. <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>');
                $(jobWrapper).find('div.alert').addClass('alert-danger');
                $(jobWrapper).find('div.alert').fadeIn(2000);
                setTimeout(hideAlert, 4000, jobWrapper);
            }
        })
    });
}
function hideAlert(element){
	    $(element).find('div.alert').fadeOut(2000, function(){
		$(element).find('div.alert').html('');
		$(element).find('div.alert').removeClass('alert-success alert-danger');
	});
}