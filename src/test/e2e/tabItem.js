describe('Dungeon - Configure Item', () => {
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

        //Change Tab
        $('/html/body/vaadin-app-layout/div/vaadin-tabs/vaadin-tab[3]').click();
    });

    it('should add a Item', () => {
        const addItem = $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[3]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');
        const nameField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]');
        const sizeField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-number-field');
        const descriptionField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[2]');
        const typeField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-combo-box');
        const saveRole = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');

        addItem.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameField.click();
        browser.keys('Test');
        sizeField.click();
        browser.keys('9');
        descriptionField.click();
        browser.keys('Test');
        typeField.click();
        browser.keys('DEFAULT')
        browser.keys('\uE007')
        saveRole.click()

        expect($('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[3]/vaadin-vertical-layout/vaadin-grid/vaadin-grid-cell-content[21]')).toBeExisting();
        expect(browser.getTitle()).toEqual('Konfigurator');
    });

    it('should add multiple Items', () => {

        const addItem = $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[3]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');
        const nameField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]');
        const sizeField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-number-field');
        const descriptionField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[2]');
        const typeField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-combo-box');
        const saveRole = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');

        addItem.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameField.click();
        browser.keys('Test1');
        sizeField.click();
        browser.keys('111');
        descriptionField.click();
        browser.keys('Test1');
        typeField.click();
        browser.keys('DEFAULT')
        browser.keys('\uE007')
        saveRole.click()

        addItem.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameField.click();
        browser.keys('Test2');
        sizeField.click();
        browser.keys('222');
        descriptionField.click();
        browser.keys('Test2');
        typeField.click();
        browser.keys('CONSUMABLE')
        browser.keys('\uE007')
        saveRole.click()

        addItem.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameField.click();
        browser.keys('Test3');
        sizeField.click();
        browser.keys('333');
        descriptionField.click();
        browser.keys('Test3');
        typeField.click();
        browser.keys('WEAPON')
        browser.keys('\uE007')
        saveRole.click()

        addItem.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameField.click();
        browser.keys('Test4');
        sizeField.click();
        browser.keys('444');
        descriptionField.click();
        browser.keys('Test4');
        typeField.click();
        browser.keys('HELMET')
        browser.keys('\uE007')
        saveRole.click()

        addItem.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameField.click();
        browser.keys('Test5');
        sizeField.click();
        browser.keys('555');
        descriptionField.click();
        browser.keys('Test5');
        typeField.click();
        browser.keys('CHESTPLATE')
        browser.keys('\uE007')
        saveRole.click()

        addItem.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameField.click();
        browser.keys('Test6');
        sizeField.click();
        browser.keys('666');
        descriptionField.click();
        browser.keys('Test6');
        typeField.click();
        browser.keys('PANTS')
        browser.keys('\uE007')
        saveRole.click()

        addItem.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameField.click();
        browser.keys('Test7');
        sizeField.click();
        browser.keys('777');
        descriptionField.click();
        browser.keys('Test7');
        typeField.click();
        browser.keys('BOOTS')
        browser.keys('\uE007')
        saveRole.click()

        expect(browser.getTitle()).toEqual('Konfigurator');
    });

    it('should delete Item', () => {

        const addItem = $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[3]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');
        const nameField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]');
        const sizeField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-number-field');
        const descriptionField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[2]');
        const typeField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-combo-box');
        const saveRole = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');

        addItem.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameField.click();
        browser.keys('Test1');
        sizeField.click();
        browser.keys('111');
        descriptionField.click();
        browser.keys('Test1');
        typeField.click();
        browser.keys('DEFAULT')
        browser.keys('\uE007')
        saveRole.click()


        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[3]/vaadin-vertical-layout/vaadin-grid/vaadin-grid-cell-content[21]').click();

        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[3]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[3]').click();

        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[3]/vaadin-vertical-layout/vaadin-grid/vaadin-grid-cell-content[21]').waitForExist(20, true);
    });
})