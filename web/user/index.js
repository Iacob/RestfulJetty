
$(function() {
    $('#example').DataTable({
        language: {
            url: "../res/components/datatables-i18n/i18n/zh-CN.json"
        },
        serverSide: true,
        ajax: {
            url: '../api/user/dt-list',
            type: 'POST',
            data: {key1 : "value1"}
        },
        searching: true,
        "columns": [
            { "orderable": true },
            { "orderable": false },
            { "orderable": false },
            { "orderable": false }
          ]
    });

    $('body').loading({message: "加载中...", start: false, onStart: function(loading){
        $(".loading-shown").css("z-index", 2000);
        loading.overlay.fadeIn(150);
    }});
})

window.Public = new Object();
window.Public.reload = function() {
    $('#example').DataTable().ajax.reload(null, true);
}
window.Public.refresh = function() {
    $('#example').DataTable().ajax.reload(null, false);
}
window.Public.ui_action_save_user = function() {
    Public.show_loading();
    var postObj = new Object();
    postObj.username = $("#mm-edit-user-space").find("#mm-edit-user-space-username").val();
    postObj.password = $("#mm-edit-user-space").find("#mm-edit-user-space-password").val();
    postObj.name = $("#mm-edit-user-space").find("#mm-edit-user-space-name").val();
    postObj.info = $("#mm-edit-user-space").find("#mm-edit-user-space-info").val();

    var postStr = JSON.stringify(postObj);
    //alert(postStr);

    $.post('../api/user/add', postStr, function(data, textStatus, jqXHR) {
        console.log(data);
    }, 'json').always(function() {
        $('#mm-edit-user-space').modal('hide');
        Public.refresh();
        Public.hide_loading();
    });
}
window.Public.ui_action_delete_user = function(elem, event) {
    Public.show_loading();
    var user_id = window.user_id_to_delete;
    console.log(user_id);
    $.post('../api/user/delete', "" + user_id, function(data, textStatus, jqXHR) {
        console.log(data);
    }, 'json').always(function() {
        Public.refresh();
        Public.hide_loading();
    });
}
window.Public.ui_action_set_user_id_to_delete = function(element, event) {
    var user_id = $(element).parent().find('input:hidden').val();
    window.user_id_to_delete = user_id;
    console.log(window.user_id_to_delete);
}
window.Public.show_loading = function() {
    $("body").loading('start');

}
window.Public.hide_loading = function() {
    $("body").loading('stop');
}
