$(function() {
	plot = document.getElementById('plot');
	textresult = document.getElementById('textresult');

	var host;

    host = 'http://localhost:4567';
    const history_server = host + '/history';
    const historytext_server = host + '/historytext';
    const hyponyms_server = host + '/hyponyms';
    const hypohist_server = host + '/hypohist';
    const hypohisttext_server = host + '/hypohisttext';

    function get_params() {
        return {
            words: document.getElementById('words').value,
            startYear: document.getElementById('start').value,
            endYear: document.getElementById('end').value,
            k: document.getElementById('k').value
        }
    }

    $('#history').click(historyButton);
    $('#historytext').click(historyTextButton);
    $('#hyponyms').click(hyponymsButton);
    $('#hypohist').click(hypohistButton);
    $('#hypohisttext').click(hypohistTextButton);

    function historyButton() {
        $("#textresult").hide();
        $("#plot").show();

        var params = get_params();
        console.log(params);
        $.get({
            async: false,
            url: history_server,
            data: params,
            success: function(data) {
            	console.log(data)

                plot.src = 'data:image/png;base64,' + data;

            },
            error: function(data) {
            	console.log("error")
            	console.log(data);
            	plot.src = 'data:image/png;base64,' + data;
            },
            dataType: 'json'
        });
    }

    function historyTextButton() {
        console.log("history text call");
        $("#plot").hide();
        $("#textresult").show();

        var params = get_params();
        console.log(params);
        $.get({
            async: false,
            url: historytext_server,
            data: params,
            success: function(data) {
            	console.log(data)

                textresult.value = data;

            },
            error: function(data) {
            	console.log("error")
            	console.log(data);
            },
            dataType: 'json'
        });
    }

    function hyponymsButton() {
        console.log("hyponyms call");
        $("#plot").hide();
        $("#textresult").show();

        var params = get_params();
        console.log(params);
        $.get({
            async: false,
            url: hyponyms_server,
            data: params,
            success: function(data) {
                console.log(data)

                textresult.value = data;

            },
            error: function(data) {
                console.log("error")
                console.log(data);
            },
            dataType: 'json'
        });
    }

    function hypohistButton(){
        $("#textresult").hide();
        $("#plot").show();

        var params = get_params();
        console.log(params);
        $.get({
            async: false,
            url: hypohist_server,
            data: params,
            success: function(data) {
                console.log(data)

                plot.src = 'data:image/png;base64,' + data;


            },
            error: function(data) {
                console.log("error")
                console.log(data);
                plot.src = 'data:image/png;base64,' + data;
            },
            dataType: 'json'
        });
    }
    function hypohistTextButton(){
            console.log("hypohist text call");
            $("#plot").hide();
            $("#textresult").show();

            var params = get_params();
            console.log(params);
            $.get({
                async: false,
                url: hypohisttext_server,
                data: params,
                success: function(data) {
                    console.log(data)

                    textresult.value = data;

                },
                error: function(data) {
                    console.log("error")
                    console.log(data);
                },
                dataType: 'json'
            });
        }

});