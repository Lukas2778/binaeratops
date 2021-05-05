describe('Dungeon - Configure NPC', () => {
    beforeEach(function (done) {
        browser.url('https://localhost:8443/')
        browser.refresh()
        browser.fullscreenWindow();

        $('/html/body/vaadin-vertical-layout/vaadin-text-field').click();
        browser.keys('Test'); //Benutzername

        $('/html/body/vaadin-vertical-layout/vaadin-password-field').click();
        browser.keys('Test'); //Passwort

        $('/html/body/vaadin-vertical-layout/vaadin-button').click();

        $('/html/body/vaadin-app-layout/vaadin-vertical-layout[1]/vaadin-tabs/vaadin-tab[4]/a').click();

        $('/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout/vaadin-button[1]').waitForExist();
        $('/html/body/vaadin-app-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout/vaadin-button[1]').click();

        //create Race
        $('/html/body/vaadin-app-layout/div/vaadin-tabs/vaadin-tab[2]').click();
        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout/vaadin-button[1]').waitForExist();
        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[2]/vaadin-split-layout/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout/vaadin-button[1]').click();
        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]').waitForExist();
        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]').click();
        browser.keys('Test');
        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[2]').click();
        browser.keys('Test');
        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]').click();

        //Change Tab
        $('/html/body/vaadin-app-layout/div/vaadin-tabs/vaadin-tab[4]').click();
    });

    it('should add a NPC', () => {

        const addRole = $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[4]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');
        const nameField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]');
        const descriptionField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[2]');
        const typeField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-combo-box');
        const saveRole = $('/html/body/vaadin-dialog-overlay/flow-component-renderer/div/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');

        addRole.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameField.click();
        browser.keys('Test2');
        descriptionField.click();
        browser.keys('Test2');
        typeField.click();
        browser.keys('Test');
        browser.keys('\uE007')
        saveRole.click()

        expect($('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[4]/vaadin-vertical-layout/vaadin-grid/vaadin-grid-cell-content[16]')).toBeExisting();
        expect(browser.getTitle()).toEqual('Konfigurator');
    });

    it('should add multiple NPCs', () => {

        const addRole = $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[4]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');
        const nameField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]');
        const descriptionField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[2]');
        const typeField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-combo-box');
        const saveRole = $('/html/body/vaadin-dialog-overlay/flow-component-renderer/div/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');

        addRole.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameField.click();
        browser.keys('Test');
        descriptionField.click();
        browser.keys('Test');
        typeField.click();
        browser.keys('Test');
        browser.keys('\uE007')
        saveRole.click()

        addRole.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameField.click();
        browser.keys('Test3');
        descriptionField.click();
        browser.keys('Test3');
        typeField.click();
        browser.keys('Test');
        browser.keys('\uE007')
        saveRole.click()

        addRole.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameField.click();
        browser.keys('Test4');
        descriptionField.click();
        browser.keys('Test4');
        typeField.click();
        browser.keys('Test');
        browser.keys('\uE007')
        saveRole.click()

        expect(browser.getTitle()).toEqual('Konfigurator');
    });

    it('should delete NPC', () => {

        const addRole = $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[4]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');
        const nameField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]');
        const descriptionField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[2]');
        const typeField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-combo-box');
        const saveRole = $('/html/body/vaadin-dialog-overlay/flow-component-renderer/div/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');

        addRole.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameField.click();
        browser.keys('Test2');
        descriptionField.click();
        browser.keys('Test2');
        typeField.click();
        browser.keys('Test');
        browser.keys('\uE007')
        saveRole.click()


        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[4]/vaadin-vertical-layout/vaadin-grid/vaadin-grid-cell-content[16]').click();

        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[4]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[3]').click();

        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[4]/vaadin-vertical-layout/vaadin-grid/vaadin-grid-cell-content[16]').waitForExist(20, true);
    });
})