describe('Login & Register', () => {

    beforeEach(function (done) {
        browser.url('https://localhost:8443')
    })

    it('should load "about Us"-page', () => {

        $('/html/body/div[2]/div/ul/li[1]/a').click()

        browser.pause(20)
        expect(browser.getTitle()).toEqual('Über Uns')
    });

    it('should load "Lobby"-page', () => {

        $('/html/body/div[2]/div/ul/li[3]/a').click()

        browser.pause(20)
        expect(browser.getTitle()).toEqual('Lobby')
    });

    it('should load "myDungeons"-page', () => {

        $('/html/body/div[2]/div/ul/li[4]/a').click()

        browser.pause(20)
        expect(browser.getTitle()).toEqual('Eigene Dungeons')
    });

    it('should load "Notification"-page', () => {

        $('/html/body/div[2]/div/ul/li[5]/a').click()

        browser.pause(20)
        expect(browser.getTitle()).toEqual('Mitteilungen')
    });
});


