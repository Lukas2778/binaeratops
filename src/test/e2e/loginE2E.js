describe('Login & Register', () => {

    beforeEach(function (done) {
        browser.url('https://localhost:8443/');
        browser.refresh();
        browser.fullscreenWindow();
    })

    it('should login', () => {
        $('/html/body/vaadin-vertical-layout/vaadin-text-field').click();
        browser.keys('Test'); //Benutzername

        $('/html/body/vaadin-vertical-layout/vaadin-password-field').click();
        browser.keys('Test'); //Passwort

        $('/html/body/vaadin-vertical-layout/vaadin-button').click();

        expect(browser.getTitle()).toEqual('Über Uns');
    });
});


