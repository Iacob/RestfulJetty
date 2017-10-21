
$(function() {
    $('#example').DataTable({
        language: {
            url: "../res/components/datatables-i18n/i18n/zh-CN.json"
        },
        serverSide: true,
        ajax: {
            url: '../api/user/list',
            type: 'POST',
            data: {key1 : "value1"}
        },
        searching: true
    });
})
