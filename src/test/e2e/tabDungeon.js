describe('Dungeon', () => {
    beforeEach(function (done) {
        browser.url('https://localhost:8443/')
        browser.refresh()
        browser.fullscreenWindow();

        $('/html/body/vaadin-vertical-layout/vaadin-text-field').click();
        browser.keys('Test'); //Benutzername

        $('/html/body/vaadin-vertical-layout/vaadin-password-field').click();
        browser.keys('Test'); //Passwort

        $('/html/body/vaadin-vertical-layout/vaadin-button').click();

        browser.pause(500)

        $('/html/body/vaadin-app-layout/vaadin-vertical-layout[1]/vaadin-tabs/vaadin-tab[4]/a').click();
        $('/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout/vaadin-button[1]').click();

        $('/html/body/vaadin-app-layout/div/vaadin-tabs/vaadin-tab[1]').click();
    });

    it('should set Name', () => {

        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[1]/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-text-field[1]').click();
        browser.keys('Testing');

        expect(browser.getTitle()).toEqual('Konfigurator');
    });

    it('should set Number of Players', () => {

        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[1]/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-text-field[2]').click();
        browser.keys('200');

        expect(browser.getTitle()).toEqual('Konfigurator');
    });

    it('should add a Validation', () => {

        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[1]/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout/vaadin-button[1]').click();

        expect(browser.getTitle()).toEqual('Konfigurator');
    });
});