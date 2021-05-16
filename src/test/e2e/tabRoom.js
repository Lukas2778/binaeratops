function createRoom(a, b) {
    $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-vertical-layout/vaadin-horizontal-layout[' + (2 * a - 1) + ']/img[' + b + ']').click();
    expect($('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-vertical-layout/vaadin-horizontal-layout[' + (2 * a - 1) + ']/img[' + b + ']')).toBeExisting();
}

function deleteRoom(a, b) {
    $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-vertical-layout/vaadin-horizontal-layout[' + (2 * a - 1) + ']/img[' + b + ']').click();
    expect($('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-vertical-layout/vaadin-horizontal-layout[' + (2 * a - 1) + ']/img[' + b + ']')).toBeExisting();
    $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-vertical-layout/vaadin-vertical-layout/vaadin-button').click();
    browser.pause(450);
    expect($('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-vertical-layout/vaadin-horizontal-layout[' + (2 * a - 1) + ']/img[' + b + ']').getAttribute('src')).toEqual('https://localhost:8443/map/KarteBack.png');
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
        const sizeField = $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-integer-field');
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

    it('create all Rooms', () => {
        for (i = 1; i <= 8; i++) {
            for (j = 1; j <= 8; j++) {
                createRoom(i, j);
                browser.pause(250);
            }
        }
    });

    it('set Name for a Room', () => {
        createRoom(2,2);
        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-vertical-layout/vaadin-vertical-layout/vaadin-text-field').click();
        browser.keys('Test');
    });

    it('set Description for a Room', () => {
        createRoom(2,2);
        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-vertical-layout/vaadin-vertical-layout/vaadin-text-area').click();
        browser.keys('Test');
    });

    it('set Item for a Room', () => {
        createRoom(2,2);
        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-vertical-layout/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-vertical-layout[1]/vaadin-button').click();
        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-grid/vaadin-grid-cell-content[1]').click();
        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-button[1]').click();
        //expect('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout/vaadin-vertical-layout[1]/vaadin-list-box//div').toBeExisting();
    });

    it('set NPC for a Room', () => {
        createRoom(2,2);
        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-vertical-layout/vaadin-vertical-layout/vaadin-horizontal-layout/vaadin-vertical-layout[2]/vaadin-button').click();
        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-grid/vaadin-grid-cell-content[1]').click();
        $('//*[@id="overlay"]/flow-component-renderer/div/vaadin-vertical-layout/vaadin-button[1]').click();
        //expect('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-horizontal-layout/vaadin-vertical-layout[2]/vaadin-list-box//div').toBeExisting();
    });

    it('delete a Room', () => {
        createRoom(1,1);
        $('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[2]/vaadin-vertical-layout/vaadin-vertical-layout/vaadin-button').click();
        browser.pause(100);
        expect($('/html/body/vaadin-app-layout/div/div/vaadin-vertical-layout[5]/vaadin-split-layout/vaadin-vertical-layout[1]/vaadin-vertical-layout/vaadin-horizontal-layout[1]/img[1]').getAttribute('src')).toEqual('https://localhost:8443/map/KarteBack.png');
    });

    it('delete all Rooms', () => {
        for (i = 1; i <= 8; i++) {
            for (j = 1; j <= 8; j++) {
                browser.pause(100)
                createRoom(i, j);
                browser.pause(150);
            }
        }
        for (i = 1; i <= 8; i++) {
            for (j = 1; j <= 8; j++) {
                deleteRoom(i, j);
                browser.pause(250);
            }
        }
    });
})