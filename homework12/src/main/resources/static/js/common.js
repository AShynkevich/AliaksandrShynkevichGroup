$.fn.serializeForm = function() {
    var formData = this.serializeArray();
    var result = {};
    $.each(formData, function() {
        result[this.name] = this.value || '';
    });
    return result;
};
