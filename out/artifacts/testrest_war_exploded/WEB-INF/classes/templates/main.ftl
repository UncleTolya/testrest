<!DOCTYPE HTML>
    <html lang="en">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=utf-8">
        <title>Test Rest by tolymhlv</title>
    </head>
    <body>
        <p>This is my web page</p>
        <form>
            <label><input type="text" name="date" value="${date!""}">Date</label>
            <button type="submit"></button>
        </form>
        <#list parameters?keys as key>
            ${key} = ${parameters[key]}
        </#list>
    </body>
</html>