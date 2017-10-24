
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

    $('body').loading({message: "请等待...", start: false, onStart: function(loading){
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
    Public.clearEditUserFormErrorMessages();
    var postObj = new Object();
    postObj.username = $("#mm-edit-user-space").find("#mm-edit-user-space-username").val();
    postObj.password = $("#mm-edit-user-space").find("#mm-edit-user-space-password").val();
    postObj.name = $("#mm-edit-user-space").find("#mm-edit-user-space-name").val();
    postObj.info = $("#mm-edit-user-space").find("#mm-edit-user-space-info").val();

    var user_id = $("#mm-edit-user-space").find("#mm-edit-user-space-user-id").val();
    if (S(user_id).isEmpty()) {
        var postStr = JSON.stringify(postObj);
        $.post('../api/user/add', postStr, function(data, textStatus, jqXHR) {
                console.log(data);
            }, 'json').always(function() {
                $('#mm-edit-user-space').modal('hide');
                Public.refresh();
                Public.hide_loading();
            });
    }else {
        user_id = Number(user_id)
        if (!Number.isNaN(user_id)) {
            postObj.id = user_id;
            var postStr = JSON.stringify(postObj);
            $.post('../api/user/edit', postStr, function(data, textStatus, jqXHR) {
                console.log(data);
            }, 'json').always(function() {
                $('#mm-edit-user-space').modal('hide');
                Public.refresh();
                Public.hide_loading();
            });
        }else {
            $('#mm-edit-user-space').modal('hide');
            Public.refresh();
            Public.hide_loading();
        }

    }


}
window.Public.ui_action_show_edit_user_form = function(element, event) {
    Public.show_loading();
    Public.resetEditUserForm();
//    var postObj = new Object();
//    postObj.username = $("#mm-edit-user-space").find("#mm-edit-user-space-username").val();
//    postObj.password = $("#mm-edit-user-space").find("#mm-edit-user-space-password").val();
//    postObj.name = $("#mm-edit-user-space").find("#mm-edit-user-space-name").val();
//    postObj.info = $("#mm-edit-user-space").find("#mm-edit-user-space-info").val();
//
//    var postStr = JSON.stringify(postObj);

    var user_id = $(element).parent().find('input:hidden').val();

    $.get('../api/user/get/'+user_id, function(data, textStatus, jqXHR) {
        if (data.ret == 0) {
            Public.ui_action_show_edit_user_form();

            $("#mm-edit-user-space").find("#mm-edit-user-space-user-id").val(data.data.id);
            $("#mm-edit-user-space").find("#mm-edit-user-space-username").val(data.data.username);
            $("#mm-edit-user-space").find("#mm-edit-user-space-password").val(data.data.password);
            $("#mm-edit-user-space").find("#mm-edit-user-space-name").val(data.data.name);
            $("#mm-edit-user-space").find("#mm-edit-user-space-info").val(data.data.info);

            $("#mm-edit-user-space").modal('show');
        }else {
            alert(data.msg);
        }
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
window.Public.resetEditUserForm = function() {
    Public.clearEditUserFormErrorMessages();
    $('#mm-edit-user-space').find('input[type="text"]').val('');
    $('#mm-edit-user-space').find('input[type="password"]').val('');
    $('#mm-edit-user-space').find('input:hidden').val('');
}
window.Public.clearEditUserFormErrorMessages = function() {
    $('#mm-edit-user-space').find('.is-invalid').removeClass('is-invalid');
    $('#mm-edit-user-space').find('.invalid-feedback').hide();
}
