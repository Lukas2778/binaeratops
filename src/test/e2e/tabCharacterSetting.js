describe('Dungeon - Charactersettings', () => {
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
        $('/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout/vaadin-button[1]').waitForExist();
        $('/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout/vaadin-button[1]').click();

        $('/html/body/vaadin-app-layout/div/vaadin-tabs/vaadin-tab[2]').click();
    });

    it('should set Inventorysize', () => {

        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-vertical-layout/vaadin-number-field').click();
        browser.keys('123');

        expect(browser.getTitle()).toEqual('Konfigurator');
    });

    it('should add a Role', () => {

        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-horizontal-layout/vaadin-button[1]').click();

        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();

        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]').click();
        browser.keys('Test');

        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[2]').click();
        browser.keys('Test');

        $('/html/body/vaadin-dialog-overlay/flow-component-renderer/div/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]').click();

        expect(browser.getTitle()).toEqual('Konfigurator');
    });

    it('should add multiple Roles', () => {

        const addRole = $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-horizontal-layout/vaadin-button[1]');
        const nameField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]');
        const descriptionField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[2]');
        const saveRole = $('/html/body/vaadin-dialog-overlay/flow-component-renderer/div/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');
        addRole.click();

        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();

        nameField.click();
        browser.keys('Test2');

        descriptionField.click();
        browser.keys('Test2');

        saveRole.click()

        addRole.click();

        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();

        nameField.click();
        browser.keys('Test3');

        descriptionField.click();
        browser.keys('Test3');

        saveRole.click();

        addRole.click();

        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();

        nameField.click();
        browser.keys('Test4');

        descriptionField.click();
        browser.keys('Test4');

        saveRole.click()

        expect(browser.getTitle()).toEqual('Konfigurator');
    });

    it('should delete Role', () => {
        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-horizontal-layout/vaadin-button[1]').click();

        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]').click();
        browser.keys('Test');

        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[2]').click();
        browser.keys('Test');

        $('/html/body/vaadin-dialog-overlay/flow-component-renderer/div/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]').click();



        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-grid/vaadin-grid-cell-content[7]').click();

        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-horizontal-layout/vaadin-button[2]').click();

        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-grid/vaadin-grid-cell-content[7]').waitForExist(20, true);
    });

    it('should add a Race', () => {

        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout/vaadin-button[1]').click();

        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();

        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]').click();
        browser.keys('Test');

        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[2]').click();
        browser.keys('Test');

        $('/html/body/vaadin-dialog-overlay/flow-component-renderer/div/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]').click();

        expect(browser.getTitle()).toEqual('Konfigurator');
    });

    it('should add multiple Races', () => {

        const addRole = $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout/vaadin-button[1]');
        const nameField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]');
        const descriptionField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[2]');
        const saveRole = $('/html/body/vaadin-dialog-overlay/flow-component-renderer/div/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');
        addRole.click();

        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();

        nameField.click();
        browser.keys('Test2');

        descriptionField.click();
        browser.keys('Test2');

        saveRole.click()

        addRole.click();

        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();

        nameField.click();
        browser.keys('Test3');

        descriptionField.click();
        browser.keys('Test3');

        saveRole.click();

        addRole.click();

        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();

        nameField.click();
        browser.keys('Test4');

        descriptionField.click();
        browser.keys('Test4');

        saveRole.click()

        expect(browser.getTitle()).toEqual('Konfigurator');
    });

    it('should delete Race', () => {
        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout/vaadin-button[1]').click();

        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]').click();
        browser.keys('Test');

        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[2]').click();
        browser.keys('Test');

        $('/html/body/vaadin-dialog-overlay/flow-component-renderer/div/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]').click();



        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-grid/vaadin-grid-cell-content[7]').click();

        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout/vaadin-button[2]').click();

        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-grid/vaadin-grid-cell-content[7]').waitForExist(20, true);
    });
});