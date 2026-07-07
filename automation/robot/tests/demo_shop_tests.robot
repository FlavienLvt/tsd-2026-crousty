*** Settings ***
Library          SeleniumLibrary
Resource         ../resources/common_keywords.robot
Suite Teardown   Close All Browsers

*** Variables ***
${URL}                  http://localhost:3001
${BROWSER}              chrome
${VALID_EMAIL}          demo@webshop.com
${VALID_PASSWORD}       demo123
${WRONG_PASSWORD}       wrongpassword
${ERROR_MESSAGE}        Login was unsuccessful. Please correct the errors and try again.

*** Test Cases ***
TC-001 Valid Login Shows User Email In Header
    [Documentation]    Positive test: a registered user logs in with correct credentials
    ...                and sees their email address in the page header.
    [Tags]             positive    login    TC-001
    Open Demo Web Shop            ${URL}    ${BROWSER}
    Navigate To Login Page        ${URL}
    Fill Login Form               ${VALID_EMAIL}    ${VALID_PASSWORD}
    Verify User Is Logged In      ${VALID_EMAIL}
    Close Demo Web Shop

TC-004 Invalid Login Shows Error Message
    [Documentation]    Negative test: a user who provides a wrong password sees an error
    ...                message and is not redirected to the home page.
    [Tags]             negative    login    TC-004
    Open Demo Web Shop            ${URL}    ${BROWSER}
    Navigate To Login Page        ${URL}
    Fill Login Form               ${VALID_EMAIL}    ${WRONG_PASSWORD}
    Verify Login Error Message    ${ERROR_MESSAGE}
    Close Demo Web Shop
