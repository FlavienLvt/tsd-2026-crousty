*** Settings ***
Library    SeleniumLibrary

*** Keywords ***
Open Demo Web Shop
    [Arguments]    ${url}    ${browser}
    [Documentation]    Opens the browser and navigates to the application home page.
    Open Browser             ${url}    ${browser}
    Maximize Browser Window

Navigate To Login Page
    [Arguments]    ${base_url}
    [Documentation]    Goes to the login route and waits for the email field to be ready.
    Go To                            ${base_url}/#/login
    Wait Until Element Is Visible    id:login-email    timeout=10s

Fill Login Form
    [Arguments]    ${email}    ${password}
    [Documentation]    Enters credentials into the login form and submits it.
    Input Text      id:login-email    ${email}
    Input Text      id:login-pwd      ${password}
    Click Button    css:#app button[type='submit']

Verify User Is Logged In
    [Arguments]    ${expected_email}
    [Documentation]    Asserts that the user email link is visible in the page header.
    Wait Until Element Is Visible    css:.header-actions a[href='#/account']    timeout=10s
    Element Should Contain           css:.header-actions a[href='#/account']    ${expected_email}

Verify Login Error Message
    [Arguments]    ${expected_message}
    [Documentation]    Asserts that an error banner is displayed after a failed login attempt.
    Wait Until Element Is Visible    css:.alert-error    timeout=10s
    Element Should Contain           css:.alert-error    ${expected_message}

Close Demo Web Shop
    [Documentation]    Closes the current browser window.
    Close Browser
