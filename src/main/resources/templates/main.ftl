<!DOCTYPE HTML>
    <html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>Test Rest by tolymhlv</title>
    </head>
    <body>
        <p>use that simply form to get information about</p>
        <form method="post">
            <label>input the date<input type="text" name="date" placeholder="required date"></label>
            <button type="submit">get info on the page</button>
        </form>
    <#list params as param>
        <#if param??>
            <div>${param}</div>
        </#if>
    <#else>
        No params
    </#list>
    </body>
</html>