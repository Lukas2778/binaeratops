function createRoom(a, b) {
    $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-vertical-layout/vaadin-horizontal-layout[' + (2 * a - 1) + ']/img[' + b + ']').click()
}

describe('Dungeon - Configure Room', () => {
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

        //Create NPC
        $('/html/body/vaadin-app-layout/div/vaadin-tabs/vaadin-tab[4]').click();
        const addNPC = $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[4]/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');
        const nameFieldNPC = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[1]');
        const descriptionFieldNPC = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-text-field[2]');
        const typeFieldNPC = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-combo-box');
        const saveRoleNPC = $('/html/body/vaadin-dialog-overlay/flow-component-renderer/div/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-button[1]');

        addNPC.click();
        expect($('/html/body/vaadin-dialog-overlay')).toBeExisting();
        nameFieldNPC.click();
        browser.keys('Test2');
        descriptionFieldNPC.click();
        browser.keys('Test2');
        typeFieldNPC.click();
        browser.keys('Test');
        browser.keys('\uE007')
        saveRoleNPC.click()

        //Create Item
        $('/html/body/vaadin-app-layout/div/vaadin-tabs/vaadin-tab[3]').click();
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
        browser.keys('999');
        descriptionField.click();
        browser.keys('Test');
        typeField.click();
        browser.keys('DEFAULT')
        browser.keys('\uE007')
        saveRole.click()

        //Change Tab
        $('/html/body/vaadin-app-layout/div/vaadin-tabs/vaadin-tab[5]').click();
    });

    it('should create a room', () => {
        createRoom(2, 3);
    });

    it('Name a Room', () => {
        createRoom(1, 1);
    });

    it('create all Rooms', () => {
        for (i = 1; i <= 8; i++) {
            for (j = 1; j <= 8; j++) {
                createRoom(i, j);
                browser.pause(250);
            }
        }
    })
})