package io.github.scamandrill.client

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshaller._
import akka.http.scaladsl.unmarshalling._
import io.github.scamandrill.models.MandrillJsonProtocol._
import io.github.scamandrill.models._
import spray.json.RootJsonFormat

import scala.concurrent.Future

class MandrillClient(val system: ActorSystem = ActorSystem("scamandrill")) extends ScamandrillSendReceive {

  implicit val ec = system.dispatcher

  /**
    * Asks all the underlying actors to close (waiting for 1 second)
    * and then shut down the system. Users of this class are supposed to call
    * this method when they are no-longer required or the application exits.
    *
    * @see [[io.github.scamandrill.client.ScamandrillSendReceive]]
    */
  def shutdownSystem(): Unit = shutdown()

  def marshal[T: RootJsonFormat](value: T): Future[MessageEntity] = Marshal(value).to[MessageEntity]

  def unmarshal[T: FromResponseUnmarshaller]: HttpResponse => Future[T] = response => Unmarshal(response).to[T]


  /////////////////////////////////////////////////////////////
  //USER calls https://mandrillapp.com/api/docs/users.JSON.html
  /////////////////////////////////////////////////////////////

  /**
    * Validate an API key and respond to a ping
    *
    * @param key - the key of the account to use
    * @return - the string "PONG!" if successful
    */
  def usersPing(ping: MKey): Future[MPingResponse] = {
    executeQuery[MPingResponse](Endpoints.ping.endpoint, marshal(ping)) { resp => Unmarshal(resp).to[String].map(MPingResponse.apply(_)) }
  }

  /**
    * Validate an API key and respond to a ping (anal JSON parser version)
    *
    * @param key - the key of the account to use
    * @return - the string "PONG!" if successful
    */
  def usersPing2(ping: MKey): Future[MPingResponse] = {
    executeQuery[MPingResponse](Endpoints.ping2.endpoint, marshal(ping))(unmarshal[MPingResponse])
  }

  /**
    * Return the senders that have tried to use this account, both verified and unverified
    *
    * @param key - the key of the account to use
    * @return the senders that have tried to use this account, both verified and unverified
    */
  def usersSenders(ping: MKey): Future[List[MSenderDataResponse]] = {
    executeQuery[List[MSenderDataResponse]](Endpoints.senders.endpoint, marshal(ping))(unmarshal[List[MSenderDataResponse]])
  }

  /**
    * Return the information about the API-connected user
    *
    * @param key - the key of the account to use
    * @return the information about the API-connected user
    */
  def usersInfo(ping: MKey): Future[MInfoResponse] = {
    executeQuery[MInfoResponse](Endpoints.info.endpoint, marshal(ping))(unmarshal[MInfoResponse])
  }

  ////////////////////////////////////////////////////////////////////
  //MESSAGES calls https://mandrillapp.com/api/docs/messages.JSON.html
  ////////////////////////////////////////////////////////////////////

  /**
    * Send a new transactional message through Mandrill
    *
    * @param msg - the message to send
    * @return - an of structs for each recipient containing the key "email" with the email address and "status" as either "sent", "queued", or "rejected"
    */
  def messagesSend(msg: MSendMessage): Future[List[MSendResponse]] = {
    executeQuery[List[MSendResponse]](Endpoints.send.endpoint, marshal(msg))(unmarshal[List[MSendResponse]])
  }

  /**
    * Send a new transactional message through Mandrill using a template
    *
    * @param msg - the message to send
    * @return - an of structs for each recipient containing the key "email" with the email address and "status" as either "sent", "queued", or "rejected"
    */
  def messagesSendTemplate(msg: MSendTemplateMessage): Future[List[MSendResponse]] = {
    executeQuery[List[MSendResponse]](Endpoints.sendTemplate.endpoint, marshal(msg))(unmarshal[List[MSendResponse]])
  }

  /**
    * Search the content of recently sent messages and optionally narrow by date range, tags and senders.
    * This method may be called up to 20 times per minute. If you need the data more often, you can use
    * /messages/info.json to get the information for a single message, or webhooks to push activity to
    * your own application for querying.
    *
    * @param q - the search values
    * @return an array of information for a single matching message
    */
  def messagesSearch(q: MSearch): Future[List[MSearchResponse]] = {
    executeQuery[List[MSearchResponse]](Endpoints.search.endpoint, marshal(q))(unmarshal[List[MSearchResponse]])
  }

  /**
    * Search the content of recently sent messages and return the aggregated hourly stats for matching messages
    *
    * @param q - the search values
    * @return the history information
    */
  def messagesSearchTimeSeries(q: MSearchTimeSeries): Future[List[MTimeSeriesResponse]] = {
    executeQuery[List[MTimeSeriesResponse]](Endpoints.searchTimeS.endpoint, marshal(q))(unmarshal[List[MTimeSeriesResponse]])
  }

  /**
    * Get the information for a single recently sent message
    *
    * @param q - the message info (containing unique id)
    * @return the information for the message
    */
  def messagesInfo(q: MMessageInfo): Future[MMessageInfoResponse] = {
    executeQuery[MMessageInfoResponse](Endpoints.msginfo.endpoint, marshal(q))(unmarshal[MMessageInfoResponse])
  }

  /**
    * Get the full content of a recently sent message
    *
    * @param q - the message info (containing unique id)
    * @return the content of the message
    */
  def messagesContent(q: MMessageInfo): Future[MContentResponse] = {
    executeQuery[MContentResponse](Endpoints.content.endpoint, marshal(q))(unmarshal[MContentResponse])
  }

  /**
    * Parse the full MIME document for an email message, returning the content of the message broken into its constituent pieces
    *
    * @param raw - the full MIME document of an email message
    * @return the parsed message
    */
  def messagesParse(raw: MParse): Future[MParseResponse] = {
    executeQuery[MParseResponse](Endpoints.parse.endpoint, marshal(raw))(unmarshal[MParseResponse])
  }

  /**
    * Take a raw MIME document for a message, and send it exactly as if it were sent through Mandrill's SMTP servers
    *
    * @param raw - the full MIME document of an email message
    * @return an array for each recipient containing the key "email" with the email address and "status" as either "sent", "queued", or "rejected"
    */
  def messagesSendRaw(raw: MSendRaw): Future[List[MSendResponse]] = {
    executeQuery[List[MSendResponse]](Endpoints.sendraw.endpoint, marshal(raw))(unmarshal[List[MSendResponse]])
  }

  /**
    * Queries your scheduled emails by sender or recipient, or both.
    *
    * @param to - the recipient address to restrict results to
    * @return a list of up to 1000 scheduled emails
    */
  def messagesListSchedule(sc: MListSchedule): Future[List[MScheduleResponse]] = {
    executeQuery[List[MScheduleResponse]](Endpoints.listSchedule.endpoint, marshal(sc))(unmarshal[List[MScheduleResponse]])
  }

  /**
    * Cancels a scheduled email
    *
    * @param sc - the scheduled mail
    * @return information about the scheduled email that was cancelled.
    */
  def messagesCancelSchedule(sc: MCancelSchedule): Future[MScheduleResponse] = {
    executeQuery[MScheduleResponse](Endpoints.cancelSchedule.endpoint, marshal(sc))(unmarshal[MScheduleResponse])
  }

  /**
    * Reschedules a scheduled email.
    *
    * @param sc - the mail to reschedule
    * @return information about the scheduled email that was rescheduled.
    */
  def messagesReschedule(sc: MReSchedule): Future[MScheduleResponse] = {
    executeQuery[MScheduleResponse](Endpoints.reschedule.endpoint, marshal(sc))(unmarshal[MScheduleResponse])
  }

  ////////////////////////////////////////////////////////////
  //TAGS calls https://mandrillapp.com/api/docs/tags.JSON.html
  ////////////////////////////////////////////////////////////

  /**
    * Return all of the user-defined key information
    *
    * @param key - the key of the account to use
    * @return all of the user-defined key information
    */
  def tagList(tag: MKey): Future[List[MTagResponse]] = {
    executeQuery[List[MTagResponse]](Endpoints.taglist.endpoint, marshal(tag))(unmarshal[List[MTagResponse]])
  }

  /**
    * Deletes a tag permanently. Deleting a tag removes the tag from any messages that have been sent, and also deletes the tag's stats.
    * There is no way to undo this operation, so use it carefully.
    *
    * @param tag - the existing tag info
    * @return the tag that was deleted
    */
  def tagDelete(tag: MTagRequest): Future[MTagResponse] = {
    executeQuery[MTagResponse](Endpoints.tagdelete.endpoint, marshal(tag))(unmarshal[MTagResponse])
  }

  /**
    * Return more detailed information about a single tag, including aggregates of recent stats
    *
    * @param tag - the existing tag info
    * @return the tag asked
    */
  def tagInfo(tag: MTagRequest): Future[MTagInfoResponse] = {
    executeQuery[MTagInfoResponse](Endpoints.taginfo.endpoint, marshal(tag))(unmarshal[MTagInfoResponse])
  }

  /**
    * Return the recent history (hourly stats for the last 30 days) for a tag
    *
    * @param tag - the existing tag info
    * @return the recent history (hourly stats for the last 30 days) for a tag
    */
  def tagTimeSeries(tag: MTagRequest): Future[List[MTimeSeriesResponse]] = {
    executeQuery[List[MTimeSeriesResponse]](Endpoints.tagtimeseries.endpoint, marshal(tag))(unmarshal[List[MTimeSeriesResponse]])
  }

  /**
    * Return the recent history (hourly stats for the last 30 days) for all tags
    *
    * @param key - the key of the account to use
    * @return the recent history (hourly stats for the last 30 days) for all tags
    */
  def tagAllTimeSeries(tag: MKey): Future[List[MTimeSeriesResponse]] = {
    executeQuery[List[MTimeSeriesResponse]](Endpoints.tagalltime.endpoint, marshal(tag))(unmarshal[List[MTimeSeriesResponse]])
  }

  /////////////////////////////////////////////////////////////////
  //REJECT calls https://mandrillapp.com/api/docs/rejects.JSON.html
  /////////////////////////////////////////////////////////////////

  /**
    * Adds an email to your email rejection blacklist. Addresses that you add manually will never expire and there is no
    * reputation penalty for removing them from your blacklist. Attempting to blacklist an address that has been
    * whitelisted will have no effect.
    *
    * @param add - info about the mail to blacklist
    * @return an object containing the address and the result of the operation
    */
  def rejectAdd(reject: MRejectAdd): Future[MRejectAddResponse] = {
    executeQuery[MRejectAddResponse](Endpoints.rejadd.endpoint, marshal(reject))(unmarshal[MRejectAddResponse])
  }

  /**
    * Deletes an email rejection. There is no limit to how many rejections you can remove from your blacklist,
    * but keep in mind that each deletion has an affect on your reputation.
    *
    * @param delete - the mail to delete from the blacklist
    * @return - an object containing the address and whether the deletion succeeded.
    */
  def rejectDelete(reject: MRejectDelete): Future[MRejectDeleteResponse] = {
    executeQuery[MRejectDeleteResponse](Endpoints.regdelete.endpoint, marshal(reject))(unmarshal[MRejectDeleteResponse])
  }

  /**
    * Retrieves your email rejection blacklist. You can provide an email address to limit the results.
    * Returns up to 1000 results. By default, entries that have expired are excluded from the results;
    * set include_expired to true to include them.
    *
    * @param list - information about the list of mails to retrieve
    * @return up to 1000 results
    */
  def rejectList(reject: MRejectList): Future[List[MRejectListResponse]] = {
    executeQuery[List[MRejectListResponse]](Endpoints.rejlist.endpoint, marshal(reject))(unmarshal[List[MRejectListResponse]])
  }

  ///////////////////////////////////////////////////////////////////////
  //WHITELIST calls https://mandrillapp.com/api/docs/whitelists.JSON.html
  ///////////////////////////////////////////////////////////////////////

  /**
    * Adds an email to your email rejection whitelist. If the address is currently on your blacklist,
    * that blacklist entry will be removed automatically.
    *
    * @param mail - the mail to be added to the whitelist
    * @return an object containing the address and the result of the operation
    */
  def whitelistAdd(mail: MWhitelist): Future[MWhitelistAddResponse] = {
    executeQuery[MWhitelistAddResponse](Endpoints.wlistadd.endpoint, marshal(mail))(unmarshal[MWhitelistAddResponse])
  }

  /**
    * Removes an email address from the whitelist.
    *
    * @param mail - the mail to be removed from the whitelist
    * @return a status object containing the address and whether the deletion succeeded
    */
  def whitelistDelete(mail: MWhitelist): Future[MWhitelistDeleteResponse] = {
    executeQuery[MWhitelistDeleteResponse](Endpoints.wlistdelete.endpoint, marshal(mail))(unmarshal[MWhitelistDeleteResponse])
  }

  /**
    * Retrieves your email rejection whitelist. You can provide an email address or search prefix to limit the results.
    *
    * @param mail - the list of mails to be returned
    * @return up to 1000 results
    */
  def whitelistList(mail: MWhitelist): Future[List[MWhitelistListResponse]] = {
    executeQuery[List[MWhitelistListResponse]](Endpoints.wlistlist.endpoint, marshal(mail))(unmarshal[List[MWhitelistListResponse]])
  }

  //////////////////////////////////////////////////////////////////
  //SENDERS calls https://mandrillapp.com/api/docs/senders.JSON.html
  //////////////////////////////////////////////////////////////////

  /**
    * Return the senders that have tried to use this account.
    *
    * @param key - the key of the account to use
    * @return the senders that have tried to use this account.
    */
  def sendersList(snd: MKey): Future[List[MSendersListResp]] = {
    executeQuery[List[MSendersListResp]](Endpoints.senderslist.endpoint, marshal(snd))(unmarshal[List[MSendersListResp]])
  }

  /**
    * Returns the sender domains that have been added to this account.
    *
    * @param key - the key of the account to use
    * @return the sender domains that have been added to this account.
    */
  def sendersDomains(snd: MKey): Future[List[MSendersDomainResponses]] = {
    executeQuery[List[MSendersDomainResponses]](Endpoints.sendersdomains.endpoint, marshal(snd))(unmarshal[List[MSendersDomainResponses]])
  }

  /**
    * Adds a sender domain to your account. Sender domains are added automatically as you send,
    * but you can use this call to add them ahead of time.
    *
    * @param snd - the domain to add
    * @return information about the domain
    */
  def sendersAddDomain(snd: MSenderDomain): Future[MSendersDomainResponses] = {
    executeQuery[MSendersDomainResponses](Endpoints.sendersadddom.endpoint, marshal(snd))(unmarshal[MSendersDomainResponses])
  }


  /**
    * Checks the SPF and DKIM settings for a domain.
    * If you haven't already added this domain to your account, it will be added automatically.
    *
    * @param snd - the domain to add
    * @return information about the domain
    */
  def sendersCheckDomain(snd: MSenderDomain): Future[MSendersDomainResponses] = {
    executeQuery[MSendersDomainResponses](Endpoints.senderschkdom.endpoint, marshal(snd))(unmarshal[MSendersDomainResponses])
  }

  /**
    * Sends a verification email in order to verify ownership of a domain. Domain verification is an optional step to
    * confirm ownership of a domain. Once a domain has been verified in a Mandrill account, other accounts may not have
    * their messages signed by that domain unless they also verify the domain.
    * This prevents other Mandrill accounts from sending mail signed by your domain.
    *
    * @param snd - the verification email to send
    * @return information about the verification that was sent
    */
  def sendersVerifyDomain(snd: MSenderVerifyDomain): Future[MSendersVerifyDomResp] = {
    executeQuery[MSendersVerifyDomResp](Endpoints.sendersverdom.endpoint, marshal(snd))(unmarshal[MSendersVerifyDomResp])
  }

  /**
    * Return more detailed information about a single sender, including aggregates of recent stats
    *
    * @param snd - the email address of the sender
    * @return the detailed information on the sender
    */
  def sendersInfo(snd: MSenderAddress): Future[MSendersInfoResp] = {
    executeQuery[MSendersInfoResp](Endpoints.sendersinfo.endpoint, marshal(snd))(unmarshal[MSendersInfoResp])
  }

  /**
    * Return the recent history (hourly stats for the last 30 days) for a sender
    *
    * @param snd - the email address of the sender
    * @return the array of history information
    */
  def sendersTimeSeries(snd: MSenderAddress): Future[List[MSenderTSResponse]] = {
    executeQuery[List[MSenderTSResponse]](Endpoints.sendersts.endpoint, marshal(snd))(unmarshal[List[MSenderTSResponse]])
  }

  ////////////////////////////////////////////////////////////
  //URLS calls https://mandrillapp.com/api/docs/urls.JSON.html
  ////////////////////////////////////////////////////////////

  /**
    * Get the 100 most clicked URLs
    *
    * @param key - the key of the account to use
    * @return the 100 most clicked URLs
    */
  def urlsList(url: MKey): Future[List[MUrlResponse]] = {
    executeQuery[List[MUrlResponse]](Endpoints.urllist.endpoint, marshal(url))(unmarshal[List[MUrlResponse]])
  }

  /**
    * Return the 100 most clicked URLs that match the search query given
    *
    * @param url - a search query
    * @return the 100 most clicked URLs that match the search query given
    */
  def urlsSearch(url: MUrlSearch): Future[List[MUrlResponse]] = {
    executeQuery[List[MUrlResponse]](Endpoints.urlsearch.endpoint, marshal(url))(unmarshal[List[MUrlResponse]])
  }

  /**
    * Return the recent history (hourly stats for the last 30 days) for a url
    *
    * @param url - a search query
    * @return the recent history (hourly stats for the last 30 days) for a url
    */
  def urlsTimeSeries(url: MUrlTimeSeries): Future[List[MUrlTimeResponse]] = {
    executeQuery[List[MUrlTimeResponse]](Endpoints.urlts.endpoint, marshal(url))(unmarshal[List[MUrlTimeResponse]])
  }

  /**
    * Get the list of tracking domains set up for this account
    *
    * @param key - the key of the account to use
    * @return the list of tracking domains set up for this account
    */
  def urlsTrackingDomain(url: MKey): Future[List[MUrlDomainResponse]] = {
    executeQuery[List[MUrlDomainResponse]](Endpoints.urltrackdom.endpoint, marshal(url))(unmarshal[List[MUrlDomainResponse]])
  }

  /**
    * Checks the CNAME settings for a tracking domain. The domain must have been added already with the add-tracking-domain call
    *
    * @param url - an existing tracking domain name
    * @return information about the tracking domain
    */
  def urlsCheckTrackingDomain(url: MUrlDomain): Future[MUrlDomainResponse] = {
    executeQuery[MUrlDomainResponse](Endpoints.urlchktrackdom.endpoint, marshal(url))(unmarshal[MUrlDomainResponse])
  }

  /**
    * Add a tracking domain to your account
    *
    * @param url - a domain
    * @return information about the domain
    */
  def urlsAddTrackingDomain(url: MUrlDomain): Future[MUrlDomainResponse] = {
    executeQuery[MUrlDomainResponse](Endpoints.urladdtrackdom.endpoint, marshal(url))(unmarshal[MUrlDomainResponse])
  }

  /////////////////////////////////////////////////////////////////////
  //TEMPLATE calls https://mandrillapp.com/api/docs/templates.JSON.html
  /////////////////////////////////////////////////////////////////////

  /**
    * Add a new template
    *
    * @param template - the template
    * @return the information saved about the new template
    */
  def templateAdd(template: MTemplate): Future[MTemplateAddResponses] = {
    executeQuery[MTemplateAddResponses](Endpoints.tmpadd.endpoint, marshal(template))(unmarshal[MTemplateAddResponses])
  }

  /**
    * Get the information for an existing template
    *
    * @param template - the template
    * @return the requested template information
    */
  def templateInfo(template: MTemplateInfo): Future[MTemplateAddResponses] = {
    executeQuery[MTemplateAddResponses](Endpoints.tmpinfo.endpoint, marshal(template))(unmarshal[MTemplateAddResponses])
  }

  /**
    * Update the code for an existing template. If null is provided for any fields, the values will remain unchanged
    *
    * @param template - the template
    * @return the template that was updated
    */
  def templateUpdate(template: MTemplate): Future[MTemplateAddResponses] = {
    executeQuery[MTemplateAddResponses](Endpoints.tmpupdate.endpoint, marshal(template))(unmarshal[MTemplateAddResponses])
  }

  /**
    * Publish the content for the template. Any new messages sent using this template will start using the content that was previously in draft.
    *
    * @param template - the template
    * @return the template that was published
    */
  def templatePublish(template: MTemplateInfo): Future[MTemplateAddResponses] = {
    executeQuery[MTemplateAddResponses](Endpoints.tmppublish.endpoint, marshal(template))(unmarshal[MTemplateAddResponses])
  }

  /**
    * Delete a template
    *
    * @param template - the template
    * @return the template that was deleted
    */
  def templateDelete(template: MTemplateInfo): Future[MTemplateAddResponses] = {
    executeQuery[MTemplateAddResponses](Endpoints.tmpdelete.endpoint, marshal(template))(unmarshal[MTemplateAddResponses])
  }

  /**
    * Return a list of all the templates available to this user
    *
    * @param template - the template
    * @return an array of objects with information about each template
    */
  def templateList(template: MTemplateList): Future[List[MTemplateAddResponses]] = {
    executeQuery[List[MTemplateAddResponses]](Endpoints.tmplist.endpoint, marshal(template))(unmarshal[List[MTemplateAddResponses]])
  }

  /**
    * Return the recent history (hourly stats for the last 30 days) for a template
    *
    * @param template - the template
    * @return an array of history information
    */
  def templateTimeSeries(template: MTemplateInfo): Future[List[MTimeSeriesResponse]] = {
    executeQuery[List[MTimeSeriesResponse]](Endpoints.tmpts.endpoint, marshal(template))(unmarshal[List[MTimeSeriesResponse]])
  }

  /**
    * Inject content and optionally merge fields into a template, returning the HTML that results
    *
    * @param template - the template
    * @return the result of rendering the given template with the content and merge field values injected
    */
  def templateRender(template: MTemplateRender): Future[MTemplateRenderResponse] = {
    executeQuery[MTemplateRenderResponse](Endpoints.tmprender.endpoint, marshal(template))(unmarshal[MTemplateRenderResponse])
  }

  ////////////////////////////////////////////////////////////////////
  //WEBHOOKS calls https://mandrillapp.com/api/docs/webhooks.JSON.html
  ////////////////////////////////////////////////////////////////////

  /**
    * Get the list of all webhooks defined on the account
    *
    * @param key - the key of the account to use
    * @return the webhooks associated with the account
    */
  def webhookList(webhook: MKey): Future[List[MWebhooksResponse]] = {
    executeQuery[List[MWebhooksResponse]](Endpoints.webhlist.endpoint, marshal(webhook))(unmarshal[List[MWebhooksResponse]])
  }

  /**
    * Add a new webhook
    *
    * @param webhook
    * @return the information saved about the new webhook
    */
  def webhookAdd(webhook: MWebhook): Future[MWebhooksResponse] = {
    executeQuery[MWebhooksResponse](Endpoints.webhadd.endpoint, marshal(webhook))(unmarshal[MWebhooksResponse])
  }

  /**
    * Given the ID of an existing webhook, return the data about it
    *
    * @param webhook - the existing webhook
    * @return the information saved about the new webhook
    */
  def webhookInfo(webhook: MWebhookInfo): Future[MWebhooksResponse] = {
    executeQuery[MWebhooksResponse](Endpoints.webhinfo.endpoint, marshal(webhook))(unmarshal[MWebhooksResponse])
  }

  /**
    * Update an existing webhook
    *
    * @param webhook - the existing webhook to update
    * @return the information saved about the new webhook
    */
  def webhookUpdate(webhook: MWebhookUpdate): Future[MWebhooksResponse] = {
    executeQuery[MWebhooksResponse](Endpoints.webhupdate.endpoint, marshal(webhook))(unmarshal[MWebhooksResponse])
  }

  /**
    * Delete an existing webhook
    *
    * @param webhook - the webhook to delete
    * @return the information saved about the new webhook
    */
  def webhookDelete(webhook: MWebhookInfo): Future[MWebhooksResponse] = {
    executeQuery[MWebhooksResponse](Endpoints.webhdelete.endpoint, marshal(webhook))(unmarshal[MWebhooksResponse])
  }

  //////////////////////////////////////////////////////////////////////////
  //SUBACCOUNTS calls https://mandrillapp.com/api/docs/subaccounts.JSON.html
  //////////////////////////////////////////////////////////////////////////

  /**
    * Get the list of subaccounts defined for the account, optionally filtered by a prefix
    *
    * @param subacc - the prefix
    * @return the subaccounts for the account, up to a maximum of 1,000
    */
  def subaccountList(subacc: MSubaccountList): Future[List[MSubaccountsResponse]] = {
    executeQuery[List[MSubaccountsResponse]](Endpoints.sublist.endpoint, marshal(subacc))(unmarshal[List[MSubaccountsResponse]])
  }

  /**
    * Add a new subaccount
    *
    * @param subacc - the subaccount to add
    * @return the information saved about the new subaccount
    */
  def subaccountAdd(subacc: MSubaccount): Future[MSubaccountsResponse] = {
    executeQuery[MSubaccountsResponse](Endpoints.subadd.endpoint, marshal(subacc))(unmarshal[MSubaccountsResponse])
  }

  /**
    * Given the ID of an existing subaccount, return the data about it
    *
    * @param subacc - the existing subaccount
    * @return the information about the subaccount
    */
  def subaccountInfo(subacc: MSubaccountInfo): Future[MSubaccountsInfoResponse] = {
    executeQuery[MSubaccountsInfoResponse](Endpoints.subinfo.endpoint, marshal(subacc))(unmarshal[MSubaccountsInfoResponse])
  }

  /**
    * Update an existing subaccount
    *
    * @param subacc - the existing subaccount to update
    * @return the information for the updated subaccount
    */
  def subaccountUpdate(subacc: MSubaccount): Future[MSubaccountsResponse] = {
    executeQuery[MSubaccountsResponse](Endpoints.subupdate.endpoint, marshal(subacc))(unmarshal[MSubaccountsResponse])
  }

  /**
    * Delete an existing subaccount. Any email related to the subaccount will be saved, but stats will be removed and any future sending calls to this subaccount will fail.
    *
    * @param subacc - the subaccount to delete
    * @return the information for the deleted subaccount
    */
  def subaccountDelete(subacc: MSubaccountInfo): Future[MSubaccountsResponse] = {
    executeQuery[MSubaccountsResponse](Endpoints.subdelete.endpoint, marshal(subacc))(unmarshal[MSubaccountsResponse])
  }

  /**
    * Pause a subaccount's sending. Any future emails delivered to this subaccount will be queued for a maximum of 3 days until the subaccount is resumed.
    *
    * @param subacc - the subaccount to pause
    * @return the information for the paused subaccount
    */
  def subaccountPause(subacc: MSubaccountInfo): Future[MSubaccountsResponse] = {
    executeQuery[MSubaccountsResponse](Endpoints.subpause.endpoint, marshal(subacc))(unmarshal[MSubaccountsResponse])
  }

  /**
    * Resume a paused subaccount's sending
    *
    * @param subacc - the subaccount to resume
    * @return the information for the resumed subaccount
    */
  def subaccountResume(subacc: MSubaccountInfo): Future[MSubaccountsResponse] = {
    executeQuery[MSubaccountsResponse](Endpoints.subresume.endpoint, marshal(subacc))(unmarshal[MSubaccountsResponse])
  }

  ////////////////////////////////////////////////////////////
  //INBOUND https://mandrillapp.com/api/docs/key.JSON.html
  ////////////////////////////////////////////////////////////

  /**
    * List the domains that have been configured for inbound delivery
    *
    * @param key - the key of the account to use
    * @return the inbound domains associated with the account
    */
  def inboundDomains(inbound: MKey): Future[List[MInboundDomainResponse]] = {
    executeQuery[List[MInboundDomainResponse]](Endpoints.inbdom.endpoint, marshal(inbound))(unmarshal[List[MInboundDomainResponse]])
  }

  /**
    * Add an inbound domain to your account
    *
    * @param inbound - the domain to add
    * @return information about the domain
    */
  def inboundAddDomain(inbound: MInboundDomain): Future[MInboundDomainResponse] = {
    executeQuery[MInboundDomainResponse](Endpoints.inbadddom.endpoint, marshal(inbound))(unmarshal[MInboundDomainResponse])
  }

  /**
    * Check the MX settings for an inbound domain. The domain must have already been added with the add-domain call
    *
    * @param inbound - the domain to check
    * @return information about the inbound domain
    */
  def inboundCheckDomain(inbound: MInboundDomain): Future[MInboundDomainResponse] = {
    executeQuery[MInboundDomainResponse](Endpoints.inbchkdom.endpoint, marshal(inbound))(unmarshal[MInboundDomainResponse])
  }

  /**
    * Delete an inbound domain from the account. All mail will stop routing for this domain immediately.
    *
    * @param inbound - the domain to delete
    * @return information about the inbound domain
    */
  def inboundDeleteDomain(inbound: MInboundDomain): Future[MInboundDomainResponse] = {
    executeQuery[MInboundDomainResponse](Endpoints.inbdeldom.endpoint, marshal(inbound))(unmarshal[MInboundDomainResponse])
  }

  /**
    * List the mailbox routes defined for an inbound domain
    *
    * @param inbound - the domain
    * @return the routes associated with the domain
    */
  def inboundRoutes(inbound: MInboundDomain): Future[List[MInboundRouteResponse]] = {
    executeQuery[List[MInboundRouteResponse]](Endpoints.inbroutes.endpoint, marshal(inbound))(unmarshal[List[MInboundRouteResponse]])
  }

  /**
    * Add a new mailbox route to an inbound domain
    *
    * @param inbound - the domain
    * @return the added mailbox route information
    */
  def inboundAddRoute(inbound: MInboundRoute): Future[MInboundRouteResponse] = {
    executeQuery[MInboundRouteResponse](Endpoints.inbaddroute.endpoint, marshal(inbound))(unmarshal[MInboundRouteResponse])
  }

  /**
    * Update the pattern or webhook of an existing inbound mailbox route. If null is provided for any fields, the values will remain unchanged.
    *
    * @param inbound - the route to update
    * @return the updated mailbox route information
    */
  def inboundUpdateRoute(inbound: MInboundUpdateRoute): Future[MInboundRouteResponse] = {
    executeQuery[MInboundRouteResponse](Endpoints.inbdelroute.endpoint, marshal(inbound))(unmarshal[MInboundRouteResponse])
  }

  /**
    * Delete an existing inbound mailbox route
    *
    * @param inbound - the route to delete
    * @return the deleted mailbox route information
    */
  def inboundDeleteRoute(inbound: MInboundDelRoute): Future[MInboundRouteResponse] = {
    executeQuery[MInboundRouteResponse](Endpoints.inbdelroute.endpoint, marshal(inbound))(unmarshal[MInboundRouteResponse])
  }

  /**
    * Take a raw MIME document destined for a domain with inbound domains set up, and send it to the inbound hook exactly as if it had been sent over SMTP
    *
    * @param inbound - raw MIME document
    * @return an array of the information for each recipient in the message (usually one) that matched an inbound route
    */
  def inboundSendRaw(inbound: MInboundRaw): Future[List[MInboundRawResponse]] = {
    executeQuery[List[MInboundRawResponse]](Endpoints.inbraw.endpoint, marshal(inbound))(unmarshal[List[MInboundRawResponse]])
  }

  ////////////////////////////////////////////////////////////
  //EXPORT https://mandrillapp.com/api/docs/exports.JSON.html
  ////////////////////////////////////////////////////////////

  /**
    * Returns information about an export job. If the export job's state is 'complete', the returned data will include
    * a URL you can use to fetch the results. Every export job produces a zip archive, but the format of the archive
    * is distinct for each job type.
    * The api calls that initiate exports include more details about the output format for that job type.
    *
    * @param export - the export type
    * @return the information about the export
    */
  def exportInfo(export: MExportInfo): Future[MExportResponse] = {
    executeQuery[MExportResponse](Endpoints.expinfo.endpoint, marshal(export))(unmarshal[MExportResponse])
  }

  /**
    * Returns a list of your exports.
    *
    * @param key - the key of the account to use
    * @return the account's exports
    */
  def exportList(export: MKey): Future[List[MExportResponse]] = {
    executeQuery[List[MExportResponse]](Endpoints.explist.endpoint, marshal(export))(unmarshal[List[MExportResponse]])
  }

  /**
    * Begins an export of your rejection blacklist. The blacklist will be exported to a zip archive containing a single
    * file named rejects.csv that includes the following fields: email, reason, detail, created_at, expires_at, last_event_at, expires_at.
    *
    * @param export - the export job
    * @return information about the rejects export job that was started
    */
  def exportReject(export: MExportNotify): Future[MExportResponse] = {
    executeQuery[MExportResponse](Endpoints.exprec.endpoint, marshal(export))(unmarshal[MExportResponse])
  }

  /**
    * Begins an export of your rejection whitelist. The whitelist will be exported to a zip archive containing a single
    * file named whitelist.csv that includes the following fields: email, detail, created_at.
    *
    * @param export - the export job
    * @return information about the whitelist export job that was started
    */
  def exportWhitelist(export: MExportNotify): Future[MExportResponse] = {
    executeQuery[MExportResponse](Endpoints.expwhite.endpoint, marshal(export))(unmarshal[MExportResponse])
  }

  /**
    * Begins an export of your activity history. The activity will be exported to a zip archive containing a single file
    * named activity.csv in the same format as you would be able to export from your account's activity view.
    * It includes the following fields: Date, Email Address, Sender, Subject, Status, Tags, Opens, Clicks, Bounce Detail.
    * If you have configured any custom metadata fields, they will be included in the exported data.
    *
    * @param export - the export activity
    * @return information about the activity export job that was started
    */
  def exportActivity(export: MExportActivity): Future[MExportResponse] = {
    executeQuery[MExportResponse](Endpoints.expactivity.endpoint, marshal(export))(unmarshal[MExportResponse])
  }

  ////////////////////////////////////////////////////
  //ISP https://mandrillapp.com/api/docs/ips.JSON.html
  ////////////////////////////////////////////////////

  /**
    * Lists your dedicated IPs.
    *
    * @param key - the key of the account to use
    * @return an array of structs for each dedicated IP
    */
  def ispList(isp: MKey): Future[List[MIspResponse]] = {
    executeQuery[List[MIspResponse]](Endpoints.isplist.endpoint, marshal(isp))(unmarshal[List[MIspResponse]])
  }

  /**
    * Retrieves information about a single dedicated ip.
    *
    * @param isp - the isp
    * @return Information about the dedicated ip
    */
  def ispInfo(isp: MIspIp): Future[MIspResponse] = {
    executeQuery[MIspResponse](Endpoints.ispinfo.endpoint, marshal(isp))(unmarshal[MIspResponse])
  }

  /**
    * Requests an additional dedicated IP for your account. Accounts may have one outstanding request at any time,
    * and provisioning requests are processed within 24 hours.
    *
    * @param isp - the isp
    * @return a description of the provisioning request that was created
    */
  def ispProvision(isp: MIspPool): Future[MIspProvisionResp] = {
    executeQuery[MIspProvisionResp](Endpoints.ispprov.endpoint, marshal(isp))(unmarshal[MIspProvisionResp])
  }

  /**
    * Begins the warmup process for a dedicated IP. During the warmup process, Mandrill will gradually increase the
    * percentage of your mail that is sent over the warming-up IP, over a period of roughly 30 days. The rest of your
    * mail will be sent over shared IPs or other dedicated IPs in the same pool.
    *
    * @param isp - the isp
    * @return Information about the dedicated IP
    */
  def ispStartWarmup(isp: MIspIp): Future[MIspResponse] = {
    executeQuery[MIspResponse](Endpoints.ispstwarm.endpoint, marshal(isp))(unmarshal[MIspResponse])
  }

  /**
    * Cancels the warmup process for a dedicated IP.
    *
    * @param isp - the isp
    * @return Information about the dedicated IP
    */
  def ispCancelWarmup(isp: MIspIp): Future[MIspResponse] = {
    executeQuery[MIspResponse](Endpoints.ispcancwarm.endpoint, marshal(isp))(unmarshal[MIspResponse])
  }

  /**
    * Moves a dedicated IP to a different pool.
    *
    * @param isp - the isp
    * @return Information about the updated state of the dedicated IP
    */
  def ispSetPool(isp: MIspSetPool): Future[MIspResponse] = {
    executeQuery[MIspResponse](Endpoints.ispsetpool.endpoint, marshal(isp))(unmarshal[MIspResponse])
  }

  /**
    * Deletes a dedicated IP. This is permanent and cannot be undone.
    *
    * @param isp - the ip
    * @return a description of the ip that was removed from your account.
    */
  def ispDelete(isp: MIspIp): Future[MIspDelete] = {
    executeQuery[MIspDelete](Endpoints.ispdel.endpoint, marshal(isp))(unmarshal[MIspDelete])
  }

  /**
    * Lists your dedicated IP pools.
    *
    * @param key - the key of the account to use
    * @return the dedicated IP pools for your account, up to a maximum of 1,000
    */
  def ispListPool(isp: MKey): Future[List[MIspInfoPool]] = {
    executeQuery[List[MIspInfoPool]](Endpoints.isplistpool.endpoint, marshal(isp))(unmarshal[List[MIspInfoPool]])
  }

  /**
    * Describes a single dedicated IP pool.
    *
    * @param isp - the ip pool
    * @return Information about the dedicated ip pool
    */
  def ispPoolInfo(isp: MIspPoolInfo): Future[MIspInfoPool] = {
    executeQuery[MIspInfoPool](Endpoints.isppoolinfo.endpoint, marshal(isp))(unmarshal[MIspInfoPool])
  }

  /**
    * Creates a pool and returns it. If a pool already exists with this name, no action will be performed.
    *
    * @param isp - the pool
    * @return Information about the dedicated ip pool
    */
  def ispCreatePool(isp: MIspPoolInfo): Future[MIspInfoPool] = {
    executeQuery[MIspInfoPool](Endpoints.ispcreatep.endpoint, marshal(isp))(unmarshal[MIspInfoPool])
  }

  /**
    * Deletes a pool. A pool must be empty before you can delete it, and you cannot delete your default pool.
    *
    * @param isp - the pool
    * @return information about the status of the pool that was deleted
    */
  def ispDeletePool(isp: MIspPoolInfo): Future[MIspDeletePoolResponse] = {
    executeQuery[MIspDeletePoolResponse](Endpoints.ispdeletep.endpoint, marshal(isp))(unmarshal[MIspDeletePoolResponse])
  }

  /**
    * Configures the custom DNS name for a dedicated IP.
    *
    * @param isp - custom dns
    * @return information about the dedicated IP's new configuration
    */
  def ispSetCustomDns(isp: MIspDns): Future[MIspDnsResponse] = {
    executeQuery[MIspDnsResponse](Endpoints.ispdns.endpoint, marshal(isp))(unmarshal[MIspDnsResponse])
  }

  //////////////////////////////////////////////////////////////
  //METADATA https://mandrillapp.com/api/docs/metadata.JSON.html
  //////////////////////////////////////////////////////////////

  /**
    * Get the list of custom metadata fields indexed for the account.
    *
    * @param key - the key of the account to use
    * @return the custom metadata fields for the account
    */
  def metadataList(meta: MKey): Future[List[MIMetadataResponse]] = {
    executeQuery[List[MIMetadataResponse]](Endpoints.metalist.endpoint, marshal(meta))(unmarshal[List[MIMetadataResponse]])
  }

  /**
    * Add a new custom metadata field to be indexed for the account.
    *
    * @param meta - the metadata to add
    * @return the information saved about the new metadata field
    */
  def metadataAdd(meta: MMeteadatapAdd): Future[MIMetadataResponse] = {
    executeQuery[MIMetadataResponse](Endpoints.metaadd.endpoint, marshal(meta))(unmarshal[MIMetadataResponse])
  }

  /**
    * Update an existing custom metadata field.
    *
    * @param meta - the metadata to update
    * @return the information for the updated metadata field
    */
  def metadataUpdate(meta: MMeteadatapAdd): Future[MIMetadataResponse] = {
    executeQuery[MIMetadataResponse](Endpoints.metaupdate.endpoint, marshal(meta))(unmarshal[MIMetadataResponse])
  }

  /**
    * Delete an existing custom metadata field. Deletion isn't instataneous, and /metadata/list will continue to return
    * the field until the asynchronous deletion process is complete.
    *
    * @param meta - the metadata to delete
    * @return the information for the deleted metadata field
    */
  def metadataDelete(meta: MMeteadatapDelete): Future[MIMetadataResponse] = {
    executeQuery[MIMetadataResponse](Endpoints.metadel.endpoint, marshal(meta))(unmarshal[MIMetadataResponse])
  }

  /**
    * An enumeration of the endpoints for Mandrill API
    *
    * @see https://mandrillapp.com/api/docs/
    */
  object Endpoints extends Enumeration {
    //users
    val ping = Value("ping", "/users/ping.json")
    val ping2 = Value("ping2", "/users/ping2.json")
    val senders = Value("senders", "/users/senders.json")
    val info = Value("info", "/users/info.json")
    //messages
    val send = Value("send", "/messages/send.json")
    val sendTemplate = Value("sendtemplate", "/messages/send-template.json")
    val search = Value("search", "/messages/search.json")
    val searchTimeS = Value("searchts", "/messages/search-time-series.json")
    val msginfo = Value("msginfo", "/messages/info.json")
    val content = Value("content", "/messages/content.json")
    val parse = Value("parse", "/messages/parse.json")
    val sendraw = Value("sendraw", "/messages/send-raw.json")
    val listSchedule = Value("sclist", "/messages/list-scheduled.json")
    val cancelSchedule = Value("sccanc", "/messages/cancel-scheduled.json")
    val reschedule = Value("scre", "/messages/reschedule.json")
    //tags
    val taglist = Value("taglist", "/tags/list.json")
    val tagdelete = Value("tagdelete", "/tags/delete.json")
    val taginfo = Value("taginfo", "/tags/info.json")
    val tagtimeseries = Value("tagts", "/tags/time-series.json")
    val tagalltime = Value("tagallts", "/tags/all-time-series.json")
    //rejects
    val rejadd = Value("rejadd", "/rejects/add.json")
    val rejlist = Value("rejlist", "/rejects/list.json")
    val regdelete = Value("regdelete", "/rejects/delete.json")
    //whitelist
    val wlistadd = Value("wlistadd", "/whitelists/add.json")
    val wlistdelete = Value("wlistdelete", "/whitelists/delete.json")
    val wlistlist = Value("wlistlist", "/whitelists/list.json")
    //senders
    val senderslist = Value("senderslist", "/senders/list.json")
    val sendersdomains = Value("sendersdom", "/senders/domains.json")
    val sendersadddom = Value("sendersadddom", "/senders/add-domain.json")
    val senderschkdom = Value("senderscheck", "/senders/check-domain.json")
    val sendersverdom = Value("sendersverdom", "/senders/verify-domain.json")
    val sendersinfo = Value("sendersinfo", "/senders/info.json")
    val sendersts = Value("sendersts", "/senders/time-series.json")
    //key
    val urllist = Value("urllist", "/urls/list.json")
    val urlsearch = Value("urlsearch", "/urls/search.json")
    val urlts = Value("urlts", "/urls/time-series.json")
    val urltrackdom = Value("urltrackdom", "/urls/tracking-domains.json")
    val urladdtrackdom = Value("urladdtrackdm", "/urls/add-tracking-domain.json")
    val urlchktrackdom = Value("urlchktrackdm", "/urls/check-tracking-domain.json")
    //template
    val tmpadd = Value("tmpadd", "/templates/add.json")
    val tmpinfo = Value("tmpinfo", "/templates/info.json")
    val tmpupdate = Value("tmpupdate", "/templates/update.json")
    val tmppublish = Value("tmppublish", "/templates/publish.json")
    val tmpdelete = Value("tmpdelete", "/templates/delete.json")
    val tmplist = Value("tmplist", "/templates/list.json")
    val tmpts = Value("tmpts", "/templates/time-series.json")
    val tmprender = Value("tmprender", "/templates/render.json")
    //webhooks
    val webhlist = Value("webhlist", "/webhooks/list.json")
    val webhadd = Value("webhadd", "/webhooks/add.json")
    val webhinfo = Value("webhinfo", "/webhooks/info.json")
    val webhupdate = Value("webhupdate", "/webhooks/update.json")
    val webhdelete = Value("webhdelete", "/webhooks/delete.json")
    //subaccounts
    val sublist = Value("sublist", "/subaccounts/list.json")
    val subadd = Value("subadd", "/subaccounts/add.json")
    val subinfo = Value("subinfo", "/subaccounts/info.json")
    val subupdate = Value("subupdte", "/subaccounts/update.json")
    val subdelete = Value("subdelete", "/subaccounts/delete.json")
    val subpause = Value("subpause", "/subaccounts/pause.json")
    val subresume = Value("subresumt", "/subaccounts/resume.json")
    //key
    val inbdom = Value("inbdom", "/inbound/domains.json")
    val inbadddom = Value("inbadddom", "/inbound/add-domain.json")
    val inbchkdom = Value("inbchkdom", "/inbound/check-domain.json")
    val inbdeldom = Value("inbdeldom", "/inbound/delete-domain.json")
    val inbroutes = Value("inbroutes", "/inbound/routes.json")
    val inbaddroute = Value("inbaddroute", "/inbound/add-route.json")
    val inbuproute = Value("inbuproute", "/inbound/update-route.json")
    val inbdelroute = Value("inbdelroute", "/inbound/delete-route.json")
    val inbraw = Value("inbraw", "/inbound/send-raw.json")
    //key
    val expinfo = Value("expinfo", "/exports/info.json")
    val explist = Value("explist", "/exports/list.json")
    val exprec = Value("exprej", "/exports/rejects.json")
    val expwhite = Value("expwhite", "/exports/whitelist.json")
    val expactivity = Value("expact", "/exports/activity.json")
    //key
    val isplist = Value("isplist", "/ips/list.json")
    val ispinfo = Value("ispinfo", "/ips/info.json")
    val ispprov = Value("ispprov", "/ips/provision.json")
    val ispstwarm = Value("isplistw", "/ips/start-warmup.json")
    val ispcancwarm = Value("ispcancw", "/ips/cancel-warmup.json")
    val ispsetpool = Value("ispsetpool", "/ips/set-pool.json")
    val ispdel = Value("ispdel", "/ips/delete.json")
    val isplistpool = Value("isplistpool", "/ips/list-pools.json")
    val isppoolinfo = Value("ispoolinfo", "/ips/pool-info.json")
    val ispcreatep = Value("ispcreatep", "/ips/create-pool.json")
    val ispdeletep = Value("ispdelpool", "/ips/delete-pool.json")
    val ispdns = Value("ispdns", "/ips/set-custom-dns.json")
    //metadata
    val metalist = Value("metalist", "/metadata/list.json")
    val metaadd = Value("metaadd", "/metadata/add.json")
    val metaupdate = Value("metaupdate", "/metadata/update.json")
    val metadel = Value("metadel", "/metadata/delete.json")

    private[MandrillClient] final def Value(name: String, endpoint: String): MyVal = new MyVal(endpoint)

    class MyVal(val endpoint: String) extends Val(nextId, endpoint)
  }
}
