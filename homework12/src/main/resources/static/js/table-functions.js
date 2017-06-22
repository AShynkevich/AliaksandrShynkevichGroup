$('#create-note').click(function(e) {
    e.preventDefault();
    $.post(
        '/notes-rest/note',
        $('#new-note-form').serialize(),
        function(data, status, xhr) {
            alert(status);
        }
    );
});
